package com.jee.boot.controller.user ;

/**
@author yaomengke
@create 2020- 04 - 14 - 14:42

 */
class ApiDocController {



    /**
     * @apiDefine  resp_sessionid
     * @apiSuccess {String} msg 信息
     * @apiSuccess {String} sessionid <p style="color:#c678dd">会话标示</p>
     */

    /**
     * @apiDefine  resp_hash
     * @apiSuccess {String} msg 信息
     * @apiSuccess {String} hash <p style="color:#c678dd">防重发标识</p>
    **/
    /**
     * @apiDefine  resp_sessionid_hash
     * @apiSuccess {Array} result  接口状态信息
     * @apiSuccess {String} result.msg 信息
     * @apiSuccess {String} result.sessionid <p style="color:#c678dd">会话标示</p>
     * @apiSuccess {String} result.hash <p style="color:#c678dd">防重发标识</p>
     */


    /**
     * @apiDefine  req_sessionid
     * @apiParam {String} sessionid <p style="color:#c678dd">会话标示</p>
     */
	
	
	/**
     * @apiDefine  req_hsession
     * @apiHeader {String} hsession <p style="color:#c678dd">会话标示</p>
     */
	
	
	

    /**
     * @apiDefine req_sessionid_hash
     * @apiParam  {String} sessionid <p style="color:#c678dd">会话标示</p>
     * @apiParam  {String} hash <p style="color:#c678dd">防重发标识 </p>
     */
	
	
	/**
     * @apiDefine  req_hsession_hash
     * @apiHeader {String} hsession <p style="color:#c678dd">会话标示</p>
     * @apiHeader {String} hash <p style="color:#c678dd">防重发标识 </p>
     */


    /**
     * @apiDefine req_page
     * @apiParam  {Number} [pageNo=1]  页码
     * @apiParam  {Number} [pageSize=15]  每页大小
     */


    /**
     * @apiDefine resp_page
     * @apiSuccess {Object} page 分页信息
     * @apiSuccess {Number} page.pageNo 页码
     * @apiSuccess {Number} page.pageSize 每页大小
     * @apiSuccess {Number} page.totalPage 总页数
     * @apiSuccess {Number} page.totalCount 总数量
     */

    /**
     *
     *
     * @api {get} /xx 公共部分
     * @apiVersion 1.0.0
     * @apiName commonReturnExample
     * @apiDescription 公共返回
     * @apiGroup 公共部分
     *
     * @apiUse req_sessionid_hash
     * @apiSuccess {Number} code <ins>600 会话无效或者会话过期 <br>632 hash校验不通过(必传没有传或者必传传错了) <br>
     * 411  入参非法(不为空判断不通过、正则表达式不通过、超过最大值最小值设置等) <br>512 异步请求超时<br> 408 会话服务器异常<br> 
     * 404 找不到结果集 <br> 一般小于400的为业务code 每个接口会单独给出</ins>
     * @apiSuccess {String} msg  响应信息
     * @apiSuccess {String} hash 重发标志 <br> 若请求hash必须要传 则返回hash一定不为空 前端需要把不为空hash保存起来供下个接口使用
     */
}