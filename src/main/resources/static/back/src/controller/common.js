/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(function (exports) {
    var $ = layui.$,
        layer = layui.layer,
        laytpl = layui.laytpl,
        setter = layui.setter,
        view = layui.view,
        admin = layui.admin

    //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
    //……
    var btnPermission = layui.data(setter.tableName).btnList;
    //退出
    admin.events.logout = function () {
        //执行退出接口
        admin.exit();
    };

    // 编辑按钮 显隐
    editBtn = function (btnPer) {
        var editBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs event-btn"  lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return editBtn;
    };

    // 查看 按钮 显隐
    seeBtn = function (btnPer) {
        var editBtn = '<a class="layui-btn  layui-btn-warm layui-btn-xs event-btn"  lay-event="see"><i class="layui-icon layui-icon-search"></i>查看</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return editBtn;
    };

    // 使用 按钮 显隐
    useBtn = function (btnPer) {
        var useBtn = '<a class="layui-btn  layui-btn-warm layui-btn-xs event-btn"  lay-event="use"><i class="layui-icon layui-icon-radio"></i>使用</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return useBtn;
    };
    // 证书 按钮 显隐
    certBtn = function (btnPer) {
        var certBtn = '<a class="layui-btn  layui-btn-warm layui-btn-xs event-btn"  lay-event="cert"><i class="layui-icon layui-icon-upload-drag"></i>证书</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return certBtn;
    };

    // 下线 按钮 显隐
    offBtn = function (btnPer) {
        var offBtn = '<a class="layui-btn  layui-bg-green layui-btn-xs event-btn"  lay-event="off"><i class="layui-icon layui-icon-close-fill"></i>下线</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return offBtn;
    };
    //发送 消息
    wxPushBtn = function (btnPer) {
        var offBtn = '<a class="layui-btn layui-btn-normal layui-btn-sm event-btn"  lay-event="wxPush"><i class="layui-icon layui-icon-edit"></i>推送消息</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return offBtn;
    };

    // 封禁 按钮 显隐
    closureBtn = function (btnPer) {
        var editBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs event-btn"  lay-event="closure"><i class="layui-icon layui-icon-password"></i>封禁</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return editBtn;
    };

    // 删除按钮 显隐
    delBtn = function (btnPer) {
        var delBtn = '<a class="layui-btn layui-btn-danger layui-btn-xs event-btn" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return delBtn;
    };

    // 批量删除按钮 显隐
    batchDelBtn = function (btnPer) {
        var batchDelBtn = '<a class="layui-btn layui-btn-danger layui-btn-sm " lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return batchDelBtn;
    };
    // 添加按钮 显隐
    addBtn = function (btnPer) {
        var batchDelBtn = '<a class="layui-btn layui-btn-normal layui-btn-sm event-btn insertAn" data-type="add" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>新增</a>';
        if ($.inArray(btnPer, btnPermission) < 0) {
            return '';
        }
        return batchDelBtn;
    };

    // js判断空字符串、null、undefined、空格、中文空格
    isEmpty = function (obj) {
        if (obj === null)
            return true;
        if (typeof obj === 'undefined') {
            return true;
        }
        if (typeof obj === 'string') {
            if (obj === "") {
                return true;
            }
            var reg = new RegExp("^([ ]+)|([　]+)$");
            return reg.test(obj);
        }
        return false;
    };

    //对外暴露的接口
    exports('common', {});
});