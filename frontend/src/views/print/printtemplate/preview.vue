<template>
    <el-dialog
      :visible="visible"
      @close="hideModal"
      title="模板预览"
      show-close
      close-on-click-modal
      :width="(width + 20) + 'mm'">
      <div v-show="isMultiPanel" id="template-preview-printPagination" style="margin: 14px 0 0 10px;"></div>
      <el-card class="card-design">
        <div id="preview_content_design"></div>
      </el-card>
      <template slot="title">
        <div>
          <div style="margin-right: 20px">模板预览</div>
        </div>
      </template>
      <template slot="footer">
        <el-button key="close" type="info" @click="hideModal">
          关闭
        </el-button>
      </template>
    </el-dialog>
  </template>

<script>
import {hiprint} from 'vue-plugin-hiprint';

let hiprintTemplate;
export default {
  name: "templatePreview",
  data() {
    return {
      visible: false,
      spinning: true,
      waitShowPrinter: false,
      template: null,
      isMultiPanel: true,
      // 模板
      hiprintTemplate: {},
      // 纸张宽 mm
      width: 0,
      // 数据
      name: '名称',
      json: {},
      printData: {},
      // 扩展 css
      extendCss: ''
    }
  },
  methods: {
    hideModal() {
      this.visible = false
      $("link[media=print]").empty();
    },
    show(hiprintTemplate, printData, width = '210') {
      this.visible = true
      this.spinning = true
      this.width = hiprintTemplate.editingPanel ? hiprintTemplate.editingPanel.width : width;
      this.hiprintTemplate = hiprintTemplate
      this.printData = printData
      setTimeout(() => {
        // eslint-disable-next-line no-undef
        $('#preview_content_design').html(hiprintTemplate.getHtml(printData))
        this.spinning = false
      }, 500)
    },
    // show(template) {
    //   let that = this;
    //   this.visible = true
    //   this.spinning = true
    //   this.template = template
    //   debugger;
    //   this.name = template.name
    //   this.json = template.json
    //   this.printData = template.printData
    //   this.extendCss = template.extendCss || "";
    //   if (this.extendCss.length > 1) {
    //     $(this.extendCss).appendTo($("link[media=print]"));
    //   }
    //   let isMounted = $('#template-preview').length <= 0 || $('#template-preview-setting').length <= 0;
    //   do {
    //     setTimeout(() => {
    //       $('#template-preview').empty()
    //       that.hiprintTemplate = hiprintTemplate = new hiprint.PrintTemplate({
    //         template: template.json,
    //         settingContainer: '#template-preview-setting',
    //         paginationContainer: '#template-preview-printPagination'
    //       })
    //       hiprintTemplate.design($('#template-preview'))
    //       that.isMultiPanel = template.lastJson.panels.length > 1
    //       console.log('isMultiPanel', that.isMultiPanel)
    //       that.spinning = false
    //     }, 200)
    //     return
    //   } while (isMounted)
    // },
    print() {
      let that = this;
      this.waitShowPrinter = true
      this.hiprintTemplate.print(this.printData, {}, {
        callback: () => {
          this.waitShowPrinter = false
        },
        styleHandler: () => {
          return that.extendCss
        }
      })
    },
    toPdf() {
      let that = this;
      this.hiprintTemplate.toPdf(this.printData, this.name, {
        styleHandler: () => {
          return that.extendCss
        }
      });
    },
    print2() {
      if (hiprint.hiwebSocket.opened) {
        let that = this;
        this.hiprintTemplate.print2(this.printData, {
          printer: '', title: this.name,
          styleHandler: () => {
            return that.extendCss
          }
        })
      } else
        this.$message.error('请先连接直接打印客户端')
    },
  }
}

</script>
<style scoped>
.card-design {
  overflow: hidden;
  overflow-x: auto;
  overflow-y: auto;
}

/deep/ .ant-modal-body {
  padding: 0px;
}

/deep/ .ant-modal-content {
  margin-bottom: 24px;
}
</style>
