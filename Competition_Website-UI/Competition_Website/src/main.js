/**
 * 入口文件
 */

import {
  createApp
} from 'vue'
import Element from "element-plus"
import "element-plus/dist/index.css"
import VueRouter from '../router'
import App from './App.vue'


//Markdown
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import VMdPreviewHtml from '@kangc/v-md-editor/lib/preview-html';
import '@kangc/v-md-editor/lib/style/preview-html.css';

// 引入使用主题的样式
import '@kangc/v-md-editor/lib/theme/vuepress'

import Prism from 'prismjs';
import Axios from 'axios'

import store from '../store/index'


VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});

var app = createApp(App)
Axios.defaults.baseURL = 'http://localhost:8080'
app.config.globalProperties.Axios = Axios
app.use(Element).use(VueMarkdownEditor).use(VMdPreviewHtml).use(VueRouter).use(store).mount("#app")