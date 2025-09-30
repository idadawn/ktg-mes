import provider from "@/views/print/printtemplate/ProviderData";
import {hiprint} from 'vue-plugin-hiprint'
import {PRINT_DICT} from "../utils/print";
import webSite from "@/config/website";

/**
 * hiprint初始化
 */
export const hiprintMixin = {
  data() {
    return {
      hiprintTemplate: null,
      hiprintThis: null,
      hiprintInitialized: false
    };
  },
  mounted() {
    // 延迟初始化：不在页面加载时立即连接打印客户端
    // 只在用户真正需要打印时才初始化（按需加载）
  },
  methods: {
    // 按需初始化打印功能
    initHiprint() {
      if (this.hiprintInitialized) {
        return Promise.resolve();
      }
      
      return new Promise((resolve, reject) => {
        try {
          hiprint.init({
            providers: [provider],
            host: webSite.print_transfer_url,
            token: webSite.print_transfer_token
          });
          this.hiprintThis = hiprint;
          this.hiprintTemplate = new hiprint.PrintTemplate();
          this.hiprintInitialized = true;
          resolve();
        } catch (error) {
          console.warn('打印客户端初始化失败:', error.message);
          reject(error);
        }
      });
    }
  }
};
