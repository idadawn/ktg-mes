import { Message } from "element-ui";
import {getDefaultPrinter} from "../api/print/printerconfig";

/**
 * 直接打印（使用Hiprint客户端打印）
 * @param printTemplate 打印模板
 * @param  printData 打印数据
 * @param  hiprintTemplate hiprint模板
 */
export function print(printTemplate, printData, hiprintTemplate, hiprintThis) {
  if (!hiprintTemplate || !hiprintThis) {
    Message.warning('打印功能未初始化，请先连接打印客户端')
    return;
  }
  
  if (hiprintTemplate) {
    try {
      // 将打印模板封装
      hiprintTemplate.update(JSON.parse(printTemplate));
      // 根据配置好的客户端查询相应的打印机
      const client = JSON.parse(localStorage.getItem("defaultClient"))
      
      // 检查 hiwebSocket 是否存在
      if (!hiprintThis.hiwebSocket || !hiprintThis.hiwebSocket.clients) {
        Message.error('打印客户端未连接,无法直接打印')
        return;
      }
      
      const clients = Object.entries(hiprintThis.hiwebSocket.clients)
      let clientName = ""
      clients.forEach(item => {
        if (client.clientIp == item[1].ip) {
          clientName = item[0]
        }
      })
      if (clientName) {
        getDefaultPrinter(client.clientId).then(res => {
          if (res.code == 200) {
            hiprintTemplate.print2(printData, {client: clientName, printer: res.data.printerName, title: 'hiprint测试打印'});
            Message.success("打印成功")
          }
        }).catch(err => {
        })
      } else {
        Message.error('客户端未连接,无法直接打印')
      }
    } catch (e) {
      Message.error(`打印失败: ${e}`)
    }
  }
}
