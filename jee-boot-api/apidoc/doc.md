<a name="top"></a>
# jee-boot-api接口文档 v1.0.0

jee-boot-api接口文档V1

- [公共部分](#公共部分)
	- [公共部分](#公共部分)
	
- [用户管理](#用户管理)
	- [2校验密码](#2校验密码)
	- [5角色权限验证](#5角色权限验证)
	- [4获取管理员信息](#4获取管理员信息)
	- [1登录](#1登录)
	- [3重置密码](#3重置密码)
	


# <a name='公共部分'></a> 公共部分

## <a name='公共部分'></a> 公共部分
[目录](#top)

<p>公共返回</p>

	GET /xx





### 入参 

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  sessionid | String | <p style="color:#c678dd">会话标示</p>|
|  hash | String | <p style="color:#c678dd">防重发标识 </p>|



### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p><ins>600 会话无效或者会话过期 <br>632 hash校验不通过(必传没有传或者必传传错了) <br> 411  入参非法(不为空判断不通过、正则表达式不通过、超过最大值最小值设置等) <br>512 异步请求超时<br> 408 会话服务器异常<br> 530 Tc调用异常透传<br> 404 找不到结果集 <br> 一般小于400的为业务code 每个接口会单独给出</ins></p>|
|  msg | String | <p>响应信息</p>|
|  hash | String | <p>重发标志 <br> 若请求hash必须要传 则返回hash一定不为空 前端需要把不为空hash保存起来供下个接口使用</p>|

# <a name='用户管理'></a> 用户管理

## <a name='2校验密码'></a> 2校验密码
[目录](#top)

<p>用户管理-校验密码</p>

	POST /admin/check_pwd

### 请求头部

| 字段    | 类型      | 描述                          |
|---------|-----------|--------------------------------------|
| hsession | String | <p style="color:#c678dd">会话标示</p>|
| hash | String | <p style="color:#c678dd">防重发标识 </p>|




### 入参 

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  password | String | <p>密码</p>|


### 成功示例

Success-Response:

```
HTTP/1.1 200 OK
{
  "code": 0,
  "msg": "请求成功",
  "hash": "abcd1233333"
}
```

### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p>0 请求成功<br></p>|
|  msg | String | <p>信息</p>|
|  hash | String | <p style="color:#c678dd">防重发标识</p>|

## <a name='5角色权限验证'></a> 5角色权限验证
[目录](#top)

<p>用户管理-角色权限验证</p>

	POST /admin/info

### 请求头部

| 字段    | 类型      | 描述                          |
|---------|-----------|--------------------------------------|
| hsession | String | <p style="color:#c678dd">会话标示</p>|





### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p>0 请求成功<br></p>|
|  result | Object | <p>信息</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.id | String | <p>id</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.icon | String | <p>头像</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.email | String | <p>邮箱</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.nickName | String | <p>昵称</p>|
|  msg | String | <p>信息</p>|
|  hash | String | <p style="color:#c678dd">防重发标识</p>|

## <a name='4获取管理员信息'></a> 4获取管理员信息
[目录](#top)

<p>用户管理-获取管理员信息</p>

	POST /admin/info

### 请求头部

| 字段    | 类型      | 描述                          |
|---------|-----------|--------------------------------------|
| hsession | String | <p style="color:#c678dd">会话标示</p>|





### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p>0 请求成功<br></p>|
|  result | Object | <p>信息</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.id | String | <p>id</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.icon | String | <p>头像</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.email | String | <p>邮箱</p>|
| &nbsp;&nbsp;&nbsp;&nbsp; result.nickName | String | <p>昵称</p>|
|  msg | String | <p>信息</p>|
|  hash | String | <p style="color:#c678dd">防重发标识</p>|

## <a name='1登录'></a> 1登录
[目录](#top)

<p>用户管理-登录</p>

	POST /admin/login





### 入参 

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  uuid | String | <p>序列号</p>|
|  username | String | <p>用户名</p>|
|  password | String | <p>密码</p>|
|  yzm | String | <p>验证码</p>|


### 成功示例

Success-Response:

```
HTTP/1.1 200 OK
{
  "code": 0,
  "msg": "请求成功",
  "hash": "abcd1233333" , 
  "hsession": "abcd1233333"
}
```

### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p>0 请求成功<br></p>|
|  msg | String | <p>返回信息</p>|
|  hsession | String | <p>header会话</p>|
|  hash | String | <p>防重发token</p>|

## <a name='3重置密码'></a> 3重置密码
[目录](#top)

<p>用户管理-重置密码</p>

	POST /admin/reset_pwd

### 请求头部

| 字段    | 类型      | 描述                          |
|---------|-----------|--------------------------------------|
| hsession | String | <p style="color:#c678dd">会话标示</p>|
| hash | String | <p style="color:#c678dd">防重发标识 </p>|




### 入参 

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  password | String | <p>密码</p>|


### 成功示例

Success-Response:

```
HTTP/1.1 200 OK
{
  "code": 0,
  "msg": "请求成功",
  "hash": "abcd1233333"
}
```

### 返回(200)

| 字段     | 类型       | 描述                           |
|:---------|:-----------|:--------------------------------------|
|  code | Number | <p>0 请求成功<br></p>|
|  msg | String | <p>信息</p>|
|  hash | String | <p style="color:#c678dd">防重发标识</p>|

