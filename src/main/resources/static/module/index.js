layui.define(['admin', 'layer', 'element', 'form'], function (exports) {
    var $ = layui.$;
    var admin = layui.admin;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;

    var index = {
        pageTabs: true,  // 是否开启多标签
        // 路由注册
        initRouter: function () {
            // 自动扫描side菜单注册
            $('.layui-layout-admin .layui-side .layui-nav a').each(function () {
                var url = $(this).attr('href');
                var menuName = $(this).text();
                if (url && 'javascript:;' != url) {
                    var key = url.replace(new RegExp('/'), '_');
                    $(this).attr('href', '#!' + key);
                    Q.reg(key, function () {
                        index.loadView(key, url, menuName);
                    });
                }
            });
            // 主页
            Q.init({
                index: 'home_console'
            });
        },
        // 路由加载组件
        loadView: function (menuId, menuPath, menuName) {
            var contentDom = '.layui-layout-admin .layui-body';
            admin.showLoading('.layui-layout-admin .layui-body');
            var flag;  // 选项卡是否已添加
            // 判断是否开启了选项卡功能
            if (index.pageTabs) {
                $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title>li').each(function (index) {
                    if ($(this).attr('lay-id') === menuId) {
                        flag = true;
                    }
                });
                if (!flag) {
                    element.tabAdd('admin-pagetabs', {
                        title: menuName,
                        content: '<div id="' + menuId + '"></div>',
                        id: menuId
                    });
                }
                contentDom = '#' + menuId;
                element.tabChange('admin-pagetabs', menuId);
                admin.rollPage('auto');
            }
            if (!flag || admin.isRefresh) {
                $(contentDom).load(menuPath, function () {
                    admin.isRefresh = false;
                    element.render('breadcrumb');
                    form.render('select');
                    admin.removeLoading('.layui-layout-admin .layui-body');
                });
            } else {
                admin.removeLoading('.layui-layout-admin .layui-body');
            }
            admin.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (document.body.clientWidth <= 750) {
                admin.flexible(true);
            }
        },
        // 检查多标签功能是否开启
        checkPageTabs: function () {
            if (index.pageTabs) {
                $('.layui-layout-admin').addClass('open-tab');
                // 如果开启多标签先加载主页
                element.tabAdd('admin-pagetabs', {
                    title: '<i class="layui-icon layui-icon-home"></i>',
                    content: '<div id="console"></div>',
                    id: 'home_console'
                });
                $('#console').load('home/console');
            } else {
                $('.layui-layout-admin').removeClass('open-tab');
            }
        },
        // 打开新页面
        openNewTab: function (param) {
            var url = param.url;
            var title = param.title;
            var menuId = param.menuId;
            if (!menuId) {
                menuId = url.replace(new RegExp('/'), '_');
            }
            index.loadView(menuId, url, title);
        },
        // 关闭选项卡
        closeTab: function (menuId) {
            element.tabDelete('admin-pagetabs', menuId);
        }
    };

    // tab选项卡切换监听
    element.on('tab(admin-pagetabs)', function (data) {
        var layId = $(this).attr('lay-id');
        Q.go(layId);
    });

    // 退出登录点击事件
    $('#btnLogout').click(function () {
        layer.confirm('确定退出登录？', function () {
            location.replace('logout');
        });
    });

    // 修改密码点击事件
    $('#setPsw').click(function () {
        admin.popupRight('home/password');
    });

    // 个人信息点击事件
    $('#setInfo').click(function () {

    });

    // 消息点击事件
    $('#btnMessage').click(function () {
        admin.popupRight('home/message');
    });

    exports('index', index);
});
