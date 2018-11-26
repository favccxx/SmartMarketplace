import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/** note: Submenu only appear when children.length>=1
 *
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'shop',
    children: [
      {
        path: 'shop',
        component: () => import('@/views/shop/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
      }
    ]
  },
  {
    path: '/sale',
    component: Layout,
    redirect: '/sale/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/sale/index'),
        name: 'Sale',
        meta: { title: 'saleProduct', icon: 'nested', noCache: true }
      },
      {
        path: 'buy/:id(\\d+)',
        component: () => import('@/views/sale/buy'),
        name: 'BuyProduct',
        meta: { title: 'buyProduct', icon: 'example', noCache: true },
        hidden: true
      },
      {
        path: 'placeorder/:id(\\d+)/:quantity(\\d+)',
        component: () => import('@/views/sale/placeorder'),
        name: 'PlaceOrder',
        meta: { title: 'confirmOrder', icon: 'example', noCache: true },
        hidden: true
      },
      {
        path: 'pay/:id(\\d+)/',
        component: () => import('@/views/sale/pay'),
        name: 'PayOrder',
        meta: { title: 'payOrder', icon: 'example', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/iorder',
    component: Layout,
    redirect: '/iorder/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/iorder/index'),
        name: 'IOrder',
        meta: { title: 'myOrder', icon: 'tree', noCache: true }
      }
    ]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/catalog',
    component: Layout,
    redirect: '/catalog/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/catalog/index'),
        name: 'Catalog',
        meta: {
          roles: ['root'],
          title: 'catalogManagement',
          icon: 'component',
          noCache: true }
      }
    ]
  },
  {
    path: '/product',
    component: Layout,
    redirect: '/product/index',
    name: 'Product',
    meta: {
      roles: ['root', 'admin'],
      title: 'productManagement',
      icon: 'example'
    },
    children: [
      {
        path: 'create',
        component: () => import('@/views/product/create'),
        name: 'CreateProduct',
        meta: {
          title: 'createProduct',
          icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/product/edit'),
        name: 'EditProduct',
        meta: { title: 'editProduct', noCache: true },
        hidden: true
      },
      {
        path: 'index',
        component: () => import('@/views/product/index'),
        name: 'Product',
        meta: { title: 'productList', icon: 'guide', noCache: true }
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    redirect: '/order/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/order/index'),
        name: 'Order',
        meta: {
          roles: ['root', 'admin'],
          title: 'orderManagement',
          icon: 'list',
          noCache: true }
      }
    ]
  },
  {
    path: '/vip',
    component: Layout,
    redirect: '/vip/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/vip/index'),
        name: 'vip',
        meta: { roles: ['root', 'admin'], title: 'vipManagement', icon: 'people', noCache: true }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/index',
    alwaysShow: true, // will always show the root menu
    meta: {
      title: 'systemManagement',
      icon: 'lock',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'staff',
        component: () => import('@/views/staff/index'),
        name: 'StaffManagement',
        meta: {
          title: 'staffManagement',
          icon: 'user',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'role',
        component: () => import('@/views/role/index'),
        name: 'RoleManagement',
        meta: {
          title: 'roleManagement',
          icon: 'theme'
        }
      },
      {
        path: 'resource',
        component: () => import('@/views/resource/index'),
        name: 'ResourceManagement',
        meta: {
          title: 'resourceManagement',
          icon: 'size'
          // if do not set roles, means: this page does not require permission
        }
      },
      {
        path: 'log',
        component: () => import('@/views/log/index'),
        name: 'AuditLog',
        meta: {
          title: 'auditLog',
          icon: 'bug',
          roles: ['admin']
          // if do not set roles, means: this page does not require permission
        }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]
