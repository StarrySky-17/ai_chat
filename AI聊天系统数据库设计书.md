# AI聊天系统数据库设计书

## 四、数据库设计

### e-r

### 实体图

### 物理结构设计如下：

#### （一）用户表（user）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| user_id | int | 用户唯一标识，主键 |
| username | varchar(50) | 用户名 |
| password | varchar(100) | 用户密码（加密存储） |
| email | varchar(100) | 用户邮箱 |
| create_time | datetime | 用户创建时间 |
| update_time | datetime | 用户信息更新时间 |
| status | tinyint | 用户状态（0：禁用，1：正常） |
| last_login_time | datetime | 最后登录时间 |

#### （二）对话表（conversation）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| conversation_id | int | 对话唯一标识，主键 |
| user_id | int | 用户ID，外键关联user表 |
| title | varchar(200) | 对话标题 |
| create_time | datetime | 对话创建时间 |
| update_time | datetime | 对话更新时间 |
| status | tinyint | 对话状态（0：删除，1：正常） |
| agent_id | int | 智能体ID，外键关联agent表 |

#### （三）消息表（message）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| message_id | int | 消息唯一标识，主键 |
| conversation_id | int | 对话ID，外键关联conversation表 |
| sender_type | tinyint | 发送者类型（0：用户，1：系统） |
| content | text | 消息内容 |
| content_type | tinyint | 内容类型（0：文本，1：图片，2：语音等） |
| send_time | datetime | 发送时间 |
| status | tinyint | 消息状态（0：未读，1：已读） |

#### （四）智能体表（agent）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| agent_id | int | 智能体唯一标识，主键 |
| name | varchar(50) | 智能体名称 |
| description | varchar(500) | 智能体描述 |
| type | tinyint | 智能体类型（0：系统内置，1：用户自定义） |
| config | text | 智能体配置参数（JSON格式） |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |
| status | tinyint | 状态（0：禁用，1：启用） |

#### （五）快捷提问表（shortcut_question）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| question_id | int | 快捷提问唯一标识，主键 |
| user_id | int | 用户ID，外键关联user表（0表示系统默认） |
| content | varchar(500) | 提问内容 |
| category | varchar(50) | 分类 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |
| status | tinyint | 状态（0：删除，1：正常） |

#### （六）用户偏好设置表（user_preference）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| preference_id | int | 偏好设置唯一标识，主键 |
| user_id | int | 用户ID，外键关联user表 |
| theme | varchar(50) | 界面主题 |
| language | varchar(20) | 语言 |
| font_size | tinyint | 字体大小 |
| notification | tinyint | 通知设置（0：关闭，1：开启） |
| update_time | datetime | 更新时间 |

#### （七）系统日志表（system_log）

| 字段名 | 数据类型 | 说明 |
| ------ | -------- | ---- |
| log_id | int | 日志唯一标识，主键 |
| user_id | int | 用户ID（可为空） |
| operation | varchar(100) | 操作描述 |
| module | varchar(50) | 操作模块 |
| ip_address | varchar(50) | IP地址 |
| operation_time | datetime | 操作时间 |
| result | tinyint | 操作结果（0：失败，1：成功） |
| error_info | text | 错误信息（失败时填写） |

## 五、索引设计

1. **用户表（user）**
   - 主键索引：user_id
   - 唯一索引：username, email
   - 普通索引：create_time, status

2. **对话表（conversation）**
   - 主键索引：conversation_id
   - 普通索引：user_id, create_time, status, agent_id

3. **消息表（message）**
   - 主键索引：message_id
   - 普通索引：conversation_id, send_time, sender_type

4. **智能体表（agent）**
   - 主键索引：agent_id
   - 普通索引：type, status

5. **快捷提问表（shortcut_question）**
   - 主键索引：question_id
   - 普通索引：user_id, category, status

## 六、关系说明

1. 一个用户（user）可以拥有多个对话（conversation），一对多关系。
2. 一个对话（conversation）可以包含多条消息（message），一对多关系。
3. 一个对话（conversation）只能关联一个智能体（agent），多对一关系。
4. 一个用户（user）可以拥有多个快捷提问（shortcut_question），一对多关系。
5. 一个用户（user）只能有一条偏好设置（user_preference），一对一关系。