# 更新记录

## v 1.2

ZUI1.2正式版终于发布了！
此版本增加了很多新特性，同时修复了大量问题，文档也得到大幅完善。欢迎大家使用并反馈问题。

### 更新明细
 * 新增 日历视图组件，方便实现日程的展示；
 * 新增 数据表格视图组件，更轻松实现复杂数据的展示，移除table.data.js；
 * 重新实现的模态框触发器，模态框触发器同时支持加载远程内容和实时内容，实时根据自身内容调整位置和尺寸，重新设计了调用接口，使用更加方便，消除了模态框触发器于一般模态框发生冲突的隐患；
 * package.json增加组件及其依赖关系的配置，重新实现了Grunt任务，编译单独组件更加方便，并支持任意组件集合的打包编译，grunt任务将自动管理组件之间的依赖关系；
 * 大幅更新文档部分章节内容，并对文档整理样式进行了调整，在文档中可以查看每个组件属性，并给出第三方组件版本及用户支持信息，文档支持IE8，优化文档在小屏幕上的表现；
 * 重新实现了漂浮消息组件，调用更方便，更好的动画效果；
 * 新增新的第三方组件chartjs，能够绘制简单图表；
 * 增加browser.js，为IE系列的浏览器增加版本提示辅助类；
 * 新增 'store.js'，实现本地存储通用接口，并增加单独页面范围的独立存储机制，beta版本中的页面标识有时不正确的bug已得到修复；
 * 新增 'jquery.extensions.js'，增加一些实用的jQuery扩展方法，便于进行其他组件的开发，移除原'unities.js'；
 * 增加array.js，提供一些操作数组的实用方法；
 * 调整了dist目录结构，第三方组件和适合单独调用的组件将直接包含在lib目录中；
 * Chosen选项及分组支持title属性，Chosen弹出列表支持自定义宽度，调整多选Chosen中选项的样式；
 * 修复Chosen中特定情况下placeholder无法显示的问题；
 * 日期时间选择器将能够自动从html标签中获取页面语言设置并应用语言设置；
 * 调整左侧固定导航样式；
 * 在date.js中增加一些实用方法来帮助进行日期计算；
 * 模态框支持额外的两种默认尺寸，修复某些时候模态框弹出时滚动条闪动的问题；
 * 修复代码段第一行出现错位的样式问题；
 * 一些组件的额外组成部分其对应的文件被重新命名；
 * 修复特定情况下kindeditor图标无法显示的问题；
 * 优化区块面板视图动作按钮事件监听机制；
 * 修复color.js中增加命名颜色支持；
 * 优化bootbox中的语言文本；
 * 表格支持固定布局样式；
 * 弹出框增加新的选项能够制定JS生成DOM的id属性，便于自定义样式；
 * 大幅优化代码，修复一些在Javascript代码中的错误，完善关键代码注释，增强部分代码文件与requierejs的兼容性。


## v 1.2 beta

### 版本亮点
 * 新增日历视图组件，方便进行日常展示；
 * 新增数据表格组件，轻松实现复杂数据展示；
 * 全新实现的模态框触发器，同时支持加载远程内容和实时内容，更好的兼容性；
 * 重新实现了Grunt任务配置，定制编译将更加方便。
 * 大量组件细节优化，文档更新。

### 更新明细
 * 新增 日历视图组件，方便实现日程的展示；
 * 新增 数据表格视图组件，更轻松实现复杂数据的展示，移除table.data.js；
 * 重新实现的模态框触发器，模态框触发器同时支持加载远程内容和实时内容，实时根据自身内容调整位置和尺寸，重新设计了调用接口，使用更加方便，消除了模态框触发器于一般模态框发生冲突的隐患；
 * package.json增加组件及其依赖关系的配置，重新实现了Grunt任务，编译单独组件更加方便，并支持任意组件集合的打包编译，grunt任务将自动管理组件之间的依赖关系；
 * 大幅更新文档部分章节内容，并对文档整理样式进行了调整，在文档中可以查看每个组件属性，并给出第三方组件版本及用户支持信息，文档支持IE8，优化文档在小屏幕上的表现；
 * 新增新的第三方组件chartjs，能够绘制简单图表；
 * 增加browser.js，为IE系列的浏览器增加版本提示辅助类；
 * 新增 'store.js'，实现本地存储通用接口，并增加单独页面范围的独立存储机制；
 * 新增 'jquery.extensions.js'，增加一些实用的jQuery扩展方法，便于进行其他组件的开发，移除原'unities.js'；
 * 增加array.js，提供一些操作数组的实用方法；
 * 调整了dist目录结构，第三方组件和适合单独调用的组件将直接包含在lib目录中；
 * Chosen选项及分组支持title属性，Chosen弹出列表支持自定义宽度，调整多选Chosen中选项的样式；
 * 修复Chosen中特定情况下placeholder无法显示的问题；
 * 日期时间选择器将能够自动从html标签中获取页面语言设置并应用语言设置；
 * 调整左侧固定导航样式；
 * 在date.js中增加一些实用方法来帮助进行日期计算；
 * 模态框支持额外的两种默认尺寸，修复某些时候模态框弹出时滚动条闪动的问题；
 * 修复代码段第一行出现错位的样式问题；
 * 一些组件的额外组成部分其对应的文件被重新命名；
 * 修复特定情况下kindeditor图标无法显示的问题；
 * 优化区块面板视图动作按钮事件监听机制；
 * 修复color.js中增加命名颜色支持；
 * 优化bootbox中的语言文本；
 * 表格支持固定布局样式；
 * 大幅优化代码，修复一些在Javascript代码中的错误，完善关键代码注释，增强部分代码文件与requierejs的兼容性。


## v 1.1
 * 新增 拖动排序插件，通过拖拽来重新给DOM节点排序；
 * 增强 Chosen组件支持为待选列表添加额外的数据用来索引；
 * 增强 拖放插件增加触发选择器参数，可以自定义拖拽事件触发的内容，此参数支持函数来动态获取值；
 * 修复 Lightbox在小屏幕上显示问题；
 * 修复 轮播在触摸屏幕上无法点击链接的问题
 * 项目网站更改为 [http://zui.sexy](http://zui.sexy)