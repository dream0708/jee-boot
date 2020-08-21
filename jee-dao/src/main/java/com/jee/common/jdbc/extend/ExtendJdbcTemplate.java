package com.jee.common.jdbc.extend;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.cache.interceptor.NamedCacheResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ExtendJdbcTemplate extends JdbcTemplate {

	private static final Map<Class<?>, RowMapper<?>> rowMappers = new ConcurrentHashMap<Class<?>, RowMapper<?>>();

	
	private int maxPageCacheNums = 1000 ;
	
	private  Map<String, String> pageCountMaps = new LinkedHashMap<String, String>(){
		 protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
		        return size() >= maxPageCacheNums ;
		 }
	} ;
	
	private  Map<String, String> pageMaps = new LinkedHashMap<String, String>(){ //防止内存过大 
		 protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
			 return size() >= maxPageCacheNums ;
		 }
	} ;
	
	private String  MAX_INI_STRING = String.valueOf(Integer.MAX_VALUE) ;
	private String  MAX_INI_STRING_1 = String.valueOf(Integer.MAX_VALUE - 1) ;
	private String  MIN_INI_STRING = String.valueOf(Integer.MIN_VALUE) ;

	private int defaultPageNo =  1;
	private int defaultPageSize = 10 ;
	
	

	private String dialect;

	public ExtendJdbcTemplate(DataSource dataSource) {
		super(dataSource);
		try {
			String driverClassName = JdbcUtils.getDriverClassName(dataSource.getConnection().getMetaData().getURL());
			dialect = JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL(), driverClassName);
		} catch (SQLException e) {
			dialect = JdbcConstants.JTDS;
		}
	}

	public <T> RowMapper<T> getObjectColumnRowMapper(Class<T> elementType) {
		if (elementType.isPrimitive() || elementType == String.class || elementType == Number.class
				|| elementType == Integer.class || elementType == Double.class || elementType == Long.class
				|| elementType == Character.class || elementType == Double.class) {
			return getSingleColumnRowMapper(elementType);
		}
		RowMapper<T> rowMpper = (RowMapper<T>) rowMappers.get(elementType);
		if (rowMpper == null) {
			rowMpper = ExtendBeanPropertyRowMapper.newInstance(elementType);
		}
		return rowMpper;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return query(sql, getObjectColumnRowMapper(elementType));
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
			throws DataAccessException {
		return query(sql, args, argTypes, getObjectColumnRowMapper(elementType));
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
		return query(sql, args, getObjectColumnRowMapper(elementType));
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, @Nullable Object... args)
			throws DataAccessException {
		return query(sql, args, getObjectColumnRowMapper(elementType));
	}

	// ------------------------------------------------------------------------------------

	protected String  countSql(String sql) {
		//log.debug("CountSql0: {} " , sql );
		String pageCountSql = pageCountMaps.get(sql);
		if (pageCountSql == null) {
			
			pageCountSql = PagerUtils.count(sql, dialect);
			pageCountMaps.put(sql, pageCountSql);
		}
		//log.debug("CountSql: {} " , pageCountSql );
		return pageCountSql ;
	}
	
	protected String  pageSql(int pageNo , int pageSize , String sql) {
		if(pageNo <= 0) {
			pageNo = defaultPageNo ;
		}
		if(pageSize <= 0) {
			pageSize = defaultPageSize ;
		}
		String pageSql = pageMaps.get(sql) ;
		if(pageSql == null) {
			if(JdbcConstants.JTDS.equals(dialect) || JdbcConstants.SQL_SERVER.equals(dialect)) {
				pageSql = PagerUtils.limit(sql, dialect,  Integer.MAX_VALUE , 1);
			}else if(JdbcConstants.MYSQL.equals(dialect)) {
				pageSql = PagerUtils.limit(sql, dialect,  Integer.MAX_VALUE , Integer.MAX_VALUE - 1);
			}else if(JdbcConstants.ORACLE.equals(dialect)) {
				pageSql = PagerUtils.limit(sql, dialect,  Integer.MAX_VALUE , 1);
			}else {
				pageSql = PagerUtils.limit(sql, dialect,  Integer.MAX_VALUE , 1);
			}
			pageMaps.put(sql, pageSql) ;
		}
		//log.debug("PageSql: {} " , pageSql );
		return pageSql.replaceAll(
				MAX_INI_STRING,  String.valueOf(( pageNo -1 ) * pageSize))
				.replace(MIN_INI_STRING, String.valueOf(pageNo * pageSize))
				.replace(MAX_INI_STRING_1, String.valueOf(pageSize));
		 
	}
	
	
	public <T> Page<T> queryForPageList(int pageNo, int pageSize, @NonNull String sql, Class<T> elementType)
			throws DataAccessException {
		int totalCount = queryForObject(countSql(sql), Integer.class);
		if (totalCount > 0) {
			String pageSql = pageSql(pageNo , pageSize , sql) ;
			return new Page<T>(pageNo, pageSize, totalCount, query(pageSql, getObjectColumnRowMapper(elementType)));
		}
		return new Page<T>(pageNo, pageSize, totalCount);
	}

	public <T> Page<T> queryForPageList(int pageNo, int pageSize, @NonNull String sql, Object[] args, int[] argTypes,
			Class<T> elementType) throws DataAccessException {

		int totalCount = queryForObject(countSql(sql), args, argTypes ,  Integer.class);
		if (totalCount > 0) {
			String pageSql = pageSql(pageNo , pageSize , sql) ;
			return new Page<T>(pageNo, pageSize, totalCount, query(pageSql, args, argTypes , getObjectColumnRowMapper(elementType)));
		}
		return new Page<T>(pageNo, pageSize, totalCount);

	}

	public <T> Page<T> queryForPageList(int pageNo, int pageSize, @NonNull String sql, Object[] args,
			Class<T> elementType) throws DataAccessException {

		int totalCount = queryForObject(countSql(sql), args ,  Integer.class);
		if (totalCount > 0) {
			String pageSql = pageSql(pageNo , pageSize , sql) ;
			return new Page<T>(pageNo, pageSize, totalCount, query(pageSql, args , getObjectColumnRowMapper(elementType)));
		}
		return new Page<T>(pageNo, pageSize, totalCount);
	}

	public <T> Page<T> queryForPageList(int pageNo, int pageSize, @NonNull String sql, Class<T> elementType,
			@Nullable Object... args) throws DataAccessException {
		int totalCount = queryForObject(countSql(sql), args ,  Integer.class);
		if (totalCount > 0) {
			String pageSql = pageSql(pageNo , pageSize , sql) ;
			return new Page<T>(pageNo, pageSize, totalCount, query(pageSql, args , getObjectColumnRowMapper(elementType)));
		}
		return new Page<T>(pageNo, pageSize, totalCount);
	}

	// -------------------------------------------------------------------------------------

	public <T> T queryForNullableObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {
		List<T> results = query(sql, args, argTypes, new RowMapperResultSetExtractor<>(rowMapper, 1));
		return nullableSingleResult(results);
	}

	public <T> T queryForNullableObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper)
			throws DataAccessException {
		List<T> results = query(sql, args, new RowMapperResultSetExtractor<>(rowMapper, 1));
		return nullableSingleResult(results);
	}

	public <T> T queryForNullableObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
			throws DataAccessException {
		List<T> results = query(sql, args, new RowMapperResultSetExtractor<>(rowMapper, 1));
		return nullableSingleResult(results);
	}

	public <T> T queryForNullableObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
			throws DataAccessException {
		return queryForNullableObject(sql, args, argTypes, getObjectColumnRowMapper(requiredType));
	}

	public <T> T queryForNullableObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
		return queryForNullableObject(sql, args, getObjectColumnRowMapper(requiredType));
	}

	public <T> T queryForNullableObject(String sql, Class<T> requiredType, @Nullable Object... args)
			throws DataAccessException {
		return queryForNullableObject(sql, args, getObjectColumnRowMapper(requiredType));
	}
    //----------------------------------------------------------------------------------
	
	public Page<Map<String, Object>> queryForPageList(int pageNo , int pageSize , String sql, Object[] args, int[] argTypes) throws DataAccessException {
		
		Integer totalCount = queryForObject(countSql(sql), args, argTypes , Integer.class);
		if (totalCount > 0) {
			return new Page<Map<String, Object>>(pageNo, pageSize, totalCount,
					queryForList(pageSql(pageNo ,  pageSize, sql) , args , argTypes));
		}
		return new Page<Map<String, Object>>(pageNo, pageSize, totalCount);
	}

	public Page<Map<String, Object>> queryForPageList(int pageNo , int pageSize , String sql, @Nullable Object... args) throws DataAccessException {
		Integer totalCount = queryForObject(countSql(sql), args , Integer.class);
		if (totalCount > 0) {
			return new Page<Map<String, Object>>(pageNo, pageSize, totalCount,
					queryForList(pageSql(pageNo ,  pageSize, sql) , args ));
		}
		return new Page<Map<String, Object>>(pageNo, pageSize, totalCount);
	}
	
	@Nullable
	public static <T> T nullableSingleResult(@Nullable Collection<T> results)
			throws IncorrectResultSizeDataAccessException {
		
		if (CollectionUtils.isEmpty(results)) {
			return null;
		}
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		}
		return results.iterator().next();
	}
	
	
	public static void main(String args[]) {
		
		LinkedHashMap<String, String> pageMaps2 = new LinkedHashMap<String, String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			 protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
			        System.out.println(eldest.getKey() +" => " + eldest.getValue()) ;
			        if(size() > 3) {
			        	return true ;
			        }
			        return false ;
			    }
		} ;
		
		pageMaps2.put("a", "a1") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		pageMaps2.put("b", "b1") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		pageMaps2.put("c", "c1") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		pageMaps2.put("d", "d1") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		pageMaps2.put("e", "e1") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		pageMaps2.put("a", "a2") ;
		System.out.println(JSON.toJSONString(pageMaps2)) ;
		
		
	}
}
