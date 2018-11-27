<p align="center" style="font-weight:bold;font-size:18px;">
  易 商 城
</p>

<p align="center">
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-2.5.17-brightgreen.svg" alt="vue">
  </a>
  <a href="https://github.com/ElemeFE/element">
    <img src="https://img.shields.io/badge/element--ui-2.4.6-brightgreen.svg" alt="element-ui">
  </a>
  <a href="https://github.com/favccxx/MarketPlace/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/mashape/apistatus.svg" alt="license">
  </a>
</p>


## 简介

[易商城](https://github.com/favccxx/SmartMarketplace) 是一个极简的商城管理系统，它基于[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)， 采用 [vue](https://github.com/vuejs/vue) 和 [element](https://github.com/ElemeFE/element)。它使用了最新的前端技术栈，内置了 i18 国际化解决方案，支持动态路由，权限管理，可以基于它快速开发小型的商城管理系统。


本项目的初始目的是采用VUE2搭建一套独立的前后端分离系统。项目以简易商城为背景，搭建一个简单的商城解决方案。

## 主要功能

```
- 登录 / 登出

- 权限管理
  - 页面权限
  - 二次登录

- 多环境发布
  - 开发环境 生产环境

- 全局特征
  - 国际化
  - 动态侧边栏
  - 标签视图

- 商品管理
  - 发布商品
  - 商品类别
  - 商品预览

- 订单管理
  - 创建订单
  - 查询订单

- 会员管理
  - 会员管理
  - 员工管理
  - 角色管理（待完善）
  - 权限管理（待完善）

- 日志管理
  - 审计日志
```

## 开发
  
  ```bash
  # 克隆项目
  git clone

  # 安装依赖
  npm install

  # 启动服务
  npm run dev
  ```

  浏览器访问 http://localhost:9527

## 发布

  ```bash
  # 构建测试环境
  npm run build:sit

  # 构建生产环境
  npm run build:prod
  ```

## 捐献

  功能太丑了，没必要

## License

[MIT](https://github.com/favccxx/SmartMarketplace/blob/master/MarketplaceWeb/LICENSE)
