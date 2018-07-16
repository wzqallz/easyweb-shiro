layui.define(['admin', 'layer', 'element'], function (exports) {
    var admin = layui.admin;
    var layer = layui.layer;
    var element = layui.element;

    var index = {
        pageTabs: true,  // 是否开启多标签
        // 路由注册
        initRouter: function () {
            // 导航点击事件
            $('.layui-layout-admin .layui-side .layui-nav a[lay-href]').click(function () {
                var menuName = $(this).text();
                var menuPath = $(this).attr('lay-href');
                if ('javascript:;' != menuPath && '' != menuPath) {
                    index.loadView(menuPath, menuName);
                    // 移动设备切换页面隐藏侧导航
                    if (document.body.clientWidth <= 750) {
                        admin.flexible(true);
                    }
                }
            });
            // tab选项卡切换监听
            element.on('tab(admin-pagetabs)', function (data) {
                var layId = $(this).attr('lay-id');
                admin.activeNav(layId);
            });
        },
        // 路由加载组件
        loadView: function (menuPath, menuName) {
            var flag;  // 选项卡是否已添加
            var contentBody = '<iframe src="' + menuPath + '" frameborder="0" class="admin-iframe" scrolling="yes"></iframe>';
            // 判断是否开启了选项卡功能
            if (index.pageTabs) {
                $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title>li').each(function () {
                    if ($(this).attr('lay-id') === menuPath) {
                        flag = true;
                        return false;
                    }
                });
                if (!flag) {
                    element.tabAdd('admin-pagetabs', {title: menuName, content: contentBody, id: menuPath});
                }
                element.tabChange('admin-pagetabs', menuPath);
                admin.rollPage('auto');
            } else {
                $('.layui-layout-admin .layui-body').html(contentBody);
            }
        },
        // 检查多标签功能是否开启
        checkPageTabs: function () {
            if (index.pageTabs) {
                $('.layui-layout-admin').addClass('open-tab');
            } else {
                $('.layui-layout-admin').removeClass('open-tab');
            }
            index.loadView('home/console', '<i class="layui-icon layui-icon-home"></i>');
            admin.activeNav('home/console');
        },
        // 打开新页面
        openNewTab: function (param) {
            var url = param.url;
            var title = param.title;
            index.loadView(url, title);
        },
        // 关闭选项卡
        closeTab: function (menuId) {
            element.tabDelete('admin-pagetabs', menuId);
        },
        // 绑定事件监听
        bindEvent: function () {
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
        }
    };

    index.bindEvent();
    exports('index', index);
});
