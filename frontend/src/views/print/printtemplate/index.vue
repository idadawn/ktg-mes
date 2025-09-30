<template>
  <div>
    <el-card>
    <div style="display: flex;flex-direction: column">
      <div style="margin-bottom: 10px">
        <el-button-group>
          <template v-for="(value,type) in paperTypes"  >
            <el-button :type="curPaperType === type ? 'primary' : 'info'" @click="setPaper(type,value)" :key="type">
              {{ type }}
            </el-button>
          </template>

          <el-popover v-model="paperPopVisible" title="设置纸张宽高(mm)" trigger="click">
              <div>
                <el-input-number v-model="paperWidth" style=" width: 120px; text-align: center"
                      controls-position="right"   placeholder="宽(mm)"/>
                <el-input style=" width: 50px; border-left: 0; pointer-events: none;text-align: center; backgroundColor: #fff"
                         placeholder="~" disabled
                />
                <el-input-number v-model="paperHeight" style="width: 120px; text-align: center; border-left: 0"
                      controls-position="right"   placeholder="高(mm)"/>
              </div>
              <el-button type="primary" style="width: 290px; margin-top: 10px;" @click="otherPaper">确定</el-button>
            <el-button slot="reference" :type="'other'==curPaperType?'primary':''">自定义纸张</el-button>
          </el-popover>
        </el-button-group>
          <el-input
            :min="scaleMin"
            :max="scaleMax"
            :step="0.1"
            disabled
            style="width: 100px; margin-left: 10px"
            :value="`${(scaleValue * 100).toFixed(0)}%`"
            :parser="value => value.replace('%', '')"
          >
            <el-button icon="el-icon-zoom-out" slot="prepend" @click="changeScale(false)"></el-button>
            <el-button icon="el-icon-zoom-in" slot="append" @click="changeScale(true)"></el-button>
          </el-input>
          <el-button-group style="margin-left: 10px;">
            <el-button type="primary" icon="el-icon-printer" @click="print()">打印</el-button>
            <el-button type="primary" icon="el-icon-refresh-left" @click="rotatePaper()">旋转</el-button>
            <el-button type="primary" icon="el-icon-view" @click="preView">预览</el-button>
            <el-button type="success" icon="el-icon-check" @click="handleSave">保存</el-button>
            <el-popconfirm
              title="是否确认清空?"
              confirm-button-text="确定清空"
              @confirm="clearPaper">
              <el-button slot="reference" type="danger" icon="el-icon-close">
                清空
              </el-button>
            </el-popconfirm>
        </el-button-group>
        <el-radio-group style="margin-left: 10px;">
          <el-radio-button @click="setElsAlign('left')" title="左对齐">
            <svg-icon icon-class="alignLeft" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('vertical')" title="居中">
            <svg-icon icon-class="alignCenter" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('right')" title="右对齐">
            <svg-icon icon-class="alignRight" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('top')" title="顶部对齐">
            <svg-icon icon-class="alignTop" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('horizontal')" title="垂直居中">
            <svg-icon icon-class="horizontal" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('bottom')" title="底部对齐">
            <svg-icon icon-class="alignBottom" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('distributeHor')" title="横向分散">
            <svg-icon icon-class="resizeHorizontal" />
          </el-radio-button>
          <el-radio-button @click="setElsAlign('distributeVer')" title="纵向分散">
            <svg-icon icon-class="resizeVertical" />
          </el-radio-button>
        </el-radio-group>
      </div>
    </div>
    <el-row :gutter="8">
      <el-col :span="4">
        <el-card class="ep-drag-container">
          <el-row>
            <el-col :span="24" class="rect-printElement-types hiprintEpContainer">
              <el-row class="drag_item_title">基础组件</el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.text" style>
                      <svg-icon icon-class="text" class="draggable-item-svg"/>
                      <p class="glyphicon-class">固定文本</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.image" style>
                      <svg-icon icon-class="picture" class="draggable-item-svg"/>
                      <p class="glyphicon-class">图片</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.longText">
                      <svg-icon icon-class="textarea" class="draggable-item-svg"/>
                      <p class="glyphicon-class">长文</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.date" style>
                      <svg-icon icon-class="date" class="draggable-item-svg"/>
                      <p class="glyphicon-class">日期时间</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row class="drag_item_title">辅助</el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.hline" style>
                      <svg-icon icon-class="hline" class="draggable-item-svg"/>
                      <p class="glyphicon-class">横线</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.vline" style>
                      <svg-icon icon-class="vline" class="draggable-item-svg"/>
                      <p class="glyphicon-class">竖线</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.rect">
                      <svg-icon icon-class="rect" class="draggable-item-svg"/>
                      <p class="glyphicon-class">矩形</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.oval">
                      <svg-icon icon-class="circle" class="draggable-item-svg"/>
                      <p class="glyphicon-class">圆形</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row class="drag_item_title">表单</el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.barcode">
                      <svg-icon icon-class="barcode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">条形码</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.qrcode">
                      <svg-icon icon-class="qrcode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">二维码</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.itemCode">
                      <svg-icon icon-class="itemcode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">物料编码</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.itemName">
                      <svg-icon icon-class="itemname" class="draggable-item-svg"/>
                      <p class="glyphicon-class">物料名称</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.workorderCode">
                      <svg-icon icon-class="workordercode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">工单编号</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.workorderName">
                      <svg-icon icon-class="workordername" class="draggable-item-svg"/>
                      <p class="glyphicon-class">工单名称</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.vendorCode">
                      <svg-icon icon-class="vendorcode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">供应商编号</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.vendorName">
                      <svg-icon icon-class="vendorname" class="draggable-item-svg"/>
                      <p class="glyphicon-class">供应商名称</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
              <el-row style="height: 100px;">
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.clientCode">
                      <svg-icon icon-class="clientcode" class="draggable-item-svg"/>
                      <p class="glyphicon-class">客户编号</p>
                    </a>
                  </div>
                </el-col>
                <el-col :span="12" class="drag_item_box">
                  <div>
                    <a class="ep-draggable-item" tid="defaultModule.clientName">
                      <svg-icon icon-class="clientname" class="draggable-item-svg"/>
                      <p class="glyphicon-class">客户名称</p>
                    </a>
                  </div>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="card-design">
          <div id="hiprint-printTemplate" class="hiprint-printTemplate"></div>
        </el-card>
      </el-col>
      <el-col :span="4" class="params_setting_container">
        <el-card>
          <el-row class="hinnn-layout-sider">
            <div id="PrintElementOptionSetting"></div>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <templatePreview id="templateView" ref="preView"></templatePreview>
    </el-card>

    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="模板编号" prop="templateCode">
              <el-input v-model="form.templateCode" placeholder="请输入模板编号" />
            </el-form-item>
          </el-col>
          <el-col :span="4"></el-col>
          <el-col :span="12">
            <el-form-item label="模板名称" prop="templateName">
              <el-input v-model="form.templateName" placeholder="请输入模板名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="模板类型" prop="templateType">
              <el-select v-model="form.templateType" placeholder="请选择模板类型">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否默认" prop="isDefault">
              <el-input v-model="form.isDefault" placeholder="请输入是否默认" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="启用状态" prop="enableFlag">
              <el-input v-model="form.enableFlag" placeholder="请输入启用状态" />
            </el-form-item>
          </el-col>
          <el-col :span="4"></el-col>
          <el-col :span="12"></el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveTemplate">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {defaultElementTypeProvider, hiprint} from 'vue-plugin-hiprint'
import panel from './panel'
import printData from './print-data'
import templatePreview from './preview.vue'
import {getTemplate,addTemplate, updateTemplate, uploadShotCut } from "@/api/print/template";
import fontSize from "./font-size.js";
import scale from "./scale.js";
import html2canvas from "html2canvas";
import provider from "@/views/print/printtemplate/ProviderData"
import {PRINT_DICT} from "@/utils/print";
import {getDefaultPrinter} from "../../../api/print/printerconfig";
import {print} from "../../../utils/print"
import webSite from "@/config/website";
import {Message} from "element-ui";
// disAutoConnect();

let hiprintTemplate;

export default {
  name: "printDesign",
  components: {templatePreview},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      hiprintThis: null,
      // 表单参数
      form: {
        templateId: this.$route.query.templateId,
        templateCode: null,
        templateType: null,
        templateName: null,
        templateJson: null,
        paperType: this.$route.query.paperType,
        templateWidth: null,
        templateHeight: null,
        templatePic: null,
        remark: null
      },
      // 表单校验
      rules: {
        templateCode: [
          { required: true, message: "模板编号不能为空", trigger: "blur" }
        ],        templateType: [
          { required: true, message: "模板类型不能为空", trigger: "change" }
        ],
      },
      template: null,
      curPaper: {
        type: 'A4',
        width: 210,
        height: 296.6
      },
      paperTypes: {
        'A3': {
          width: 420,
          height: 296.6
        },
        'A4': {
          width: 210,
          height: 296.6
        },
        'A5': {
          width: 210,
          height: 147.6
        },
        'B3': {
          width: 500,
          height: 352.6
        },
        'B4': {
          width: 250,
          height: 352.6
        },
        'B5': {
          width: 250,
          height: 175.6
        }
      },
      // 自定义纸张
      paperPopVisible: false,
      paperWidth: '80',
      paperHeight: '60',
      // 缩放
      scaleValue: 1,
      scaleMax: 5,
      scaleMin: 0.5,
      // 导入导出json
      jsonIn: '',
      jsonOut: '',
    }
  },
  computed: {
    curPaperType() {
      let type = 'other'
      let types = this.paperTypes
      for (const key in types) {
        let item = types[key]
        let {width, height} = this.curPaper
        if (item.width === width && item.height === height) {
          type = key
        }
      }
      return type
    }
  },
  mounted() {
    this.init()
  },
  created(){
    if(this.form.templateId != null && this.form.templateId != ''){
        const templateId = this.form.templateId;
        getTemplate(templateId).then(response => {
            this.form = response.data;
            if (hiprintTemplate) {
              try {
                if (this.form.paperType == 'other') {
                  const data = {
                    width: this.form.templateWidth,
                    height: this.form.templateHeight
                  }
                  this.setPaper(this.form.paperType, data)
                } else {
                  this.setPaper(this.form.paperType);
                }
                hiprintTemplate.update(JSON.parse(this.form.templateJson));
              } catch (e) {
                this.$message.error(`更新失败: ${e}`)
              }
            }
          });
    }
  },
  methods: {
    init() {
      hiprint.init({
        providers: [provider],
        host: webSite.print_transfer_url,
        token: webSite.print_transfer_token
      });
      this.hiprintThis = hiprint
      // 还原配置
      hiprint.setConfig()
      // 替换配置
      hiprint.setConfig({
        optionItems: [
          fontSize,
          scale,
          function () {
            function t() {
              this.name = "zIndex";
            }

            return t.prototype.css = function (t, e) {
              if (t && t.length) {
                if (e) return t.css('z-index', e);
              }
              return null;
            }, t.prototype.createTarget = function () {
              return this.target = $('<div class="hiprint-option-item">\n        <div class="hiprint-option-item-label">\n        元素层级2\n        </div>\n        <div class="hiprint-option-item-field">\n        <input type="number" class="auto-submit"/>\n        </div>\n    </div>'), this.target;
            }, t.prototype.getValue = function () {
              var t = this.target.find("input").val();
              if (t) return parseInt(t.toString());
            }, t.prototype.setValue = function (t) {
              this.target.find("input").val(t);
            }, t.prototype.destroy = function () {
              this.target.remove();
            }, t;
          }(),
        ],
        movingDistance: 2.5,
        text: {
          tabs: [
            // 隐藏部分
            {
              // name: '测试', // tab名称 可忽略
              options: [] // 必须包含 options
            },// 当修改第二个 tabs 时,必须把他之前的 tabs 都列举出来.
            {
              name: '样式', options: [
                {
                  name: 'scale',
                  after: 'transform', // 自定义参数，插入在 transform 之后
                  hidden: false
                },
              ]
            }
          ],
          supportOptions: [
            {
              name: 'styler',
              hidden: true
            },
            {
              name: 'scale', // 自定义参数，supportOptions 必须得添加
              after: 'transform', // 自定义参数，插入在 transform 之后
              hidden: false
            },
            {
              name: 'formatter',
              hidden: true
            },
          ]
        },
        image: {
          tabs: [
            {
              // 整体替换
              replace: true,
              name: '基本', options: [
                {
                  name: 'field',
                  hidden: false
                },
                {
                  name: 'src',
                  hidden: false
                },
                {
                  name: 'fit',
                  hidden: false
                }
              ]
            },
          ],
        }
      })
      // eslint-disable-next-line no-undef
      hiprint.PrintElementTypeManager.buildByHtml($('.ep-draggable-item'));
      $('#hiprint-printTemplate').empty()
      let that = this;
      this.template = hiprintTemplate = new hiprint.PrintTemplate({
        template: panel,
        // 图片选择功能
        onImageChooseClick: (target) => {
          // 测试 3秒后修改图片地址值
          setTimeout(() => {
            // target.refresh(url,options,callback)
            // callback(el, width, height) // 原元素,宽,高
            // target.refresh(url,false,(el,width,height)=>{
            //   el.options.width = width;
            //   el.designTarget.css('width', width + "pt");
            //   el.designTarget.children('.resize-panel').trigger($.Event('click'));
            // })
            target.refresh("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAtAAAAIIAQMAAAB99EudAAAABlBMVEUmf8vG2O41LStnAAABD0lEQVR42u3XQQqCQBSAYcWFS4/QUTpaHa2jdISWLUJjjMpclJoPGvq+1WsYfiJCZ4oCAAAAAAAAAAAAAAAAAHin6pL9c6H/fOzHbRrP0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0u/SY9LS0tLS0tLS0tLS0n+edm+UlpaWlpaWlpaWlpaW/tl0Ndyzbno7/+tPTJdd1wal69dNa6abx+Lq6TSeYtK7BX/Diek0XULSZZrakPRtV0i6Hu/KIt30q4fM0pvBqvR9mvsQkZaW9gyJT+f5lsnzjR54xAk8mAUeJyMPwYFH98ALx5Jr0kRLLndT7b64UX9QR/0eAAAAAAAAAAAAAAAAAAD/4gpryzr/bja4QgAAAABJRU5ErkJggg==", {
              // auto: true, // 根据图片宽高自动等比(宽>高?width:height)
              // width: true, // 按宽调整高
              // height: true, // 按高调整宽
              real: true // 根据图片实际尺寸调整(转pt)
            })
          }, 3000)
          // target.getValue()
          // target.refresh(url)
        },
        // 自定义可选字体
        // 或者使用 hiprintTemplate.setFontList([])
        // 或元素中 options.fontList: []
        fontList: [
          {title: '微软雅黑', value: 'Microsoft YaHei'},
          {title: '黑体', value: 'STHeitiSC-Light'},
          {title: '思源黑体', value: 'SourceHanSansCN-Normal'},
          {title: '王羲之书法体', value: '王羲之书法体'},
          {title: '宋体', value: 'SimSun'},
          {title: '华为楷体', value: 'STKaiti'},
          {title: 'cursive', value: 'cursive'},
        ],
        dataMode: 1, // 1:getJson 其他：getJsonTid 默认1
        history: true, // 是否需要 撤销重做功能
        onDataChanged: (type, json) => {
          console.log(type); // 新增、移动、删除、修改(参数调整)、大小、旋转
          console.log(json); // 返回 template
        },
        onUpdateError: (e) => {
          console.log(e);
        },
        settingContainer: '#PrintElementOptionSetting',
        paginationContainer: '.hiprint-printPagination'
      });
      hiprintTemplate.design('#hiprint-printTemplate',{ grid: true });
      console.log(hiprintTemplate);
      // 获取当前放大比例, 当zoom时传true 才会有
      this.scaleValue = hiprintTemplate.editingPanel.scale || 1;
    },
    /**
     * 设置纸张大小
     * @param type [A3, A4, A5, B3, B4, B5, other]
     * @param value {width,height} mm
     */
    setPaper(type, value) {
      try {
        this.form.paperType = type
        if (Object.keys(this.paperTypes).includes(type)) {
          this.curPaper = {type: type, width: this.paperTypes[type].width, height: this.paperTypes[type].height}
          hiprintTemplate.setPaper(this.paperTypes[type].width, this.paperTypes[type].height)
          this.form.templateWidth = this.paperTypes[type].width
          this.form.templateHeight = this.paperTypes[type].height
        } else {
          this.curPaper = {type: 'other', width: value.width, height: value.height}
          hiprintTemplate.setPaper(value.width, value.height)
          this.form.templateWidth = value.width
          this.form.templateHeight = value.height
        }
        const pageArea = this.form.templateHeight * this.form.templateWidth
        let scaleValue = this.scaleValue
        if (pageArea < 30000) {
          // 画布比例放大
          // 根据纸张宽度确定页面缩放比例
          scaleValue = Math.floor(250 / this.form.templateWidth)
        } else {
          scaleValue = 1
        }
        if (hiprintTemplate) {
          // scaleValue: 放大缩小值, false: 不保存(不传也一样), 如果传 true, 打印时也会放大
          hiprintTemplate.zoom(scaleValue);
          this.scaleValue = scaleValue;
        }
      } catch (error) {
        this.$message.error(`操作失败: ${error}`)
      }
    },
    otherPaper() {
      let value = {}
      value.width = this.paperWidth
      value.height = this.paperHeight
      this.paperPopVisible = false
      this.setPaper('other', value)
    },
    changeScale(big) {
      let scaleValue = this.scaleValue;
      if (big) {
        scaleValue += 0.1;
        if (scaleValue > this.scaleMax) scaleValue = 5;
      } else {
        scaleValue -= 0.1;
        if (scaleValue < this.scaleMin) scaleValue = 0.5;
      }
      if (hiprintTemplate) {
        // scaleValue: 放大缩小值, false: 不保存(不传也一样), 如果传 true, 打印时也会放大
        hiprintTemplate.zoom(scaleValue);
        this.scaleValue = scaleValue;
      }
    },
    rotatePaper() {
      if (hiprintTemplate) {
        hiprintTemplate.rotatePaper()
      }
    },
    preView() {
      let {width} = this.curPaper
      this.$refs.preView.show(hiprintTemplate, printData, width)
    },
    handleSave(){
      if(this.form.templateId !=null){

        this.saveTemplate();
      }else{
        this.open = true;
        this.title = "新增打印模板配置";
      }
    },
    //将当前模板的JSON保存到数据库
    saveTemplate(){
      let that = this;
      if (hiprintTemplate) {
        // this.form.templateJson = JSON.stringify(hiprintTemplate.getJson() || {});
        this.form.templateJson = JSON.stringify(hiprintTemplate.getJson())
      }
      //保存缩略图
      html2canvas(document.getElementById("hiprint-printTemplate")).then(function(canvas){
        canvas.toBlob(blob =>{
          // const href = window.URL.createObjectURL(new Blob([blob]));
          // const link = document.createElement('a');
          // link.href = href;
          // link.download = '测试图片.png';
          // document.body.appendChild(link);
          // link.click();
          // document.body.removeChild(link);
          let fileName = `${new Date().getTime()}.jpg`;
          let file = new File([blob],fileName,{type: 'image/jpg'});
          let formData = new FormData();
          formData.append('file',file);
          uploadShotCut(formData).then(response =>{
            if(response.code == '200'){
              that.form.templatePic = response.url;
            }
            if (that.form.templateId != null) {
                  updateTemplate(that.form).then(response => {
                    that.$modal.msgSuccess("修改成功");
                  });
              } else {
                  addTemplate(that.form).then(response => {
                    that.$modal.msgSuccess("新增成功");
                  });
            }
          });
        },'img/png');
      });

    },
    //当前模板的打印测试
    printText(){

    },
    onlyPrint2() {
      let that = this;
      if (window.hiwebSocket.opened) {
        let hiprintTemplate = this.$print2(undefined, panel, printData, {
          printer: '', title: 'Api单独打印',
          styleHandler: () => {
            // let css = '<link href="http://hiprint.io/Content/hiprint/css/print-lock.css" media="print" rel="stylesheet">';
            let css = '<style>.hiprint-printElement-text{color:red !important;}</style>'
            return css
          }
        })
        let key = 'Api单独直接打印';
        hiprintTemplate.on('printSuccess', function () {
          that.$notification.success({
            key: key,
            placement: 'topRight',
            message: key + ' 打印成功',
            description: 'Api单独直接打印回调',
          });
        });
        return;
      }
      this.$message.error('客户端未连接,无法直接打印')
    },
    print() {
          // 根据配置好的客户端查询相应的打印机
          const client = JSON.parse(localStorage.getItem("defaultClient"))
          const clients = Object.entries(this.hiprintThis.hiwebSocket.clients)
          let clientName = ""
          clients.forEach(item => {
            if (client.clientIp == item[1].ip) {
              clientName = item[0]
            }
          })
          if (clientName) {
            getDefaultPrinter(client.clientId).then(res => {
              if (res.code == 200) {
                hiprintTemplate.print2({}, {client: clientName, printer: res.data.printerName, title: 'hiprint测试打印'});
                Message.success("打印成功")
              }
            }).catch(err => {
            })
          } else {
            Message.error('客户端未连接,无法直接打印')
          }
    },
    clearPaper() {
      try {
        hiprintTemplate.clear();
      } catch (error) {
        this.$message.error(`操作失败: ${error}`);
      }
    },
    ippPrintAttr() {
      // 不知道打印机 ipp 情况， 可通过 '客户端' 获取一下
      const printerList = hiprintTemplate.getPrinterList();
      console.log(printerList)
      if (!printerList.length) return;
      let p = printerList[0];
      console.log(p)
      // 系统不同， 参数可能不同
      let url = p.options['printer-uri-supported'];
      // 测试 获取 ipp打印 支持参数
      hiprint.ippPrint({
        url: url,
        // 打印机参数： {version,uri,charset,language}
        opt: {},
        action: 'Get-Printer-Attributes', // 获取打印机支持参数
        // ipp参数
        message: null,
      }, (res) => {
        // 执行的ipp 任务回调 / 错误回调
        console.log(res)
      }, (printer) => {
        // ipp连接成功 回调 打印机信息
        console.log(printer)
      })
    },
    ippPrintTest() {
      // 不知道打印机 ipp 情况， 可通过 '客户端' 获取一下
      const printerList = hiprintTemplate.getPrinterList();
      console.log(printerList)
      if (!printerList.length) return;
      let p = printerList[0];
      console.log(p)
      // 系统不同， 参数可能不同
      let url = p.options['printer-uri-supported'];
      // 测试 打印文本
      hiprint.ippPrint({
        url: url,
        // 打印机参数： {version,uri,charset,language}
        opt: {},
        action: 'Print-Job',
        // ipp参数
        message: {
          "operation-attributes-tag": {
            "requesting-user-name": "hiPrint", // 用户名
            "job-name": "ipp Test Job", // 任务名
            "document-format": "text/plain" // 文档类型
          },
          // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
          // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
          // data 需为 Buffer (客户端简单处理了string 转 Buffer), 支持设置 encoding
          // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
          // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
          // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
          // 其他 Uint8Array/ArrayBuffer   默认仅 使用 Buffer.from(data)
          data: 'test test test test test test test',
          encoding: 'utf-8' // 默认可不传
        }
      }, (res) => {
        // 执行的ipp 任务回调 / 错误回调
        console.log(res)
      }, (printer) => {
        // ipp连接成功 回调 打印机信息
        console.log(printer)
      })
    },
    // 自定义 ipp 请求
    ippRequestTest() {
      const printerList = hiprintTemplate.getPrinterList();
      console.log(printerList)
      if (!printerList.length) return;
      let p = printerList[0];
      console.log(p)
      // 系统不同， 参数可能不同
      let url = p.options['printer-uri-supported'];
      // 详见： https://www.npmjs.com/package/ipp
      hiprint.ippRequest({
        url: url,
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        data: {
          "operation": "Get-Printer-Attributes",
          "operation-attributes-tag": {
            // 测试发现 Request下列3个必须要有
            "attributes-charset": "utf-8",
            "attributes-natural-language": "zh-cn",
            "printer-uri": url
          }
        }
      }, (res) => {
        // 执行的ipp 任务回调 / 错误回调
        console.log(res)
      })
    },
    ippRequestPrint() {
      const printerList = hiprintTemplate.getPrinterList();
      console.log(printerList)
      if (!printerList.length) return;
      let p = printerList[0];
      console.log(p)
      // 系统不同， 参数可能不同
      let url = p.options['printer-uri-supported'];
      let str = "ippRequestPrint ippRequestPrint ippRequestPrint";
      let array = new Uint8Array(str.length);
      for (var i = 0; i < str.length; i++) {
        array[i] = str.charCodeAt(i);
      }
      let testData = array.buffer;
      // 详见： https://www.npmjs.com/package/ipp
      hiprint.ippRequest({
        url: url,
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        // 传入的数据 ipp.serialize 后 未做任何处理  打印内容 需要 Buffer
        data: {
          "operation": "Print-Job",
          "operation-attributes-tag": {
            // 测试发现 Request下列3个必须要有
            "attributes-charset": "utf-8",
            "attributes-natural-language": "zh-cn",
            "printer-uri": url,
            "requesting-user-name": "hiPrint", // 用户名
            "job-name": "ipp Request Job", // 任务名
            "document-format": "text/plain" // 文档类型
          },
          data: testData
        }
      }, (res) => {
        // 执行的ipp 任务回调 / 错误回调
        console.log(res)
      })
    },
    updateJson() {
      if (hiprintTemplate) {
        try {
          hiprintTemplate.update(JSON.parse(this.jsonIn))
        } catch (e) {
          this.$message.error(`更新失败: ${e}`)
        }
      }
    },
    exportJson() {
      if (hiprintTemplate) {
        this.jsonOut = JSON.stringify(hiprintTemplate.getJson() || {})
      }
    },
    setElsAlign(e) {
      hiprintTemplate.setElsAlign(e)
    },
    setElsSpace(h) {
      hiprintTemplate.setElsSpace(10, h)
    },
    getSelectEls() {
      let els = hiprintTemplate.getSelectEls();
      console.log(els)
    },
    updateFontSize() {
      hiprintTemplate.updateOption('fontSize', 12);
    },
    updateFontWeight() {
      hiprintTemplate.updateOption('fontWeight', 'bolder');
    },
    cancel(){
      this.open = false;
    }
  }
}
</script>

<style scoped>

.drag_item_box {
  height: 100%;
  padding: 6px;
}

.drag_item_box > div {
  height: 100%;
  width: 100%;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}



.drag_item_box > div > a {
  text-align: center;
  text-decoration-line: none;
}

.drag_item_box > div > a > .draggable-item-svg {
  width: 50px;
  height: 50px;
}


.drag_item_box > div > a > span {
  font-size: 28px;
}

.drag_item_box > div > a > p {
  margin: 0;
}

.drag_item_title {
  font-size: 16px;
  padding: 12px 6px 0 6px;
  font-weight: bold;
}



.card-design {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  overflow-x: auto;
  overflow-y: auto;
}

.ep-drag-container {
  height: 100vh;
  overflow-y: auto;
}

</style>
