<template>
  <div class="flex-container">
    <el-image ref="image" class="barcodeClass" fit="scale-down" :src="barcodeUrl">
      <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
      </div>
    </el-image>
  </div>
</template>
<script>
import { getBarcodeUrl } from "@/api/mes/wm/barcode";

export default {
  name: 'BarcodeImg',
  props: {

    bussinessId: {
      type: Number,
      default: -1
    },
    bussinessCode: {
      type: String,
      default: null
    },
    barcodeFormart: {
      type: String,
      default: 'QR_CODE'
    },
    barcodeType: {
      type: String,
      default : null
    },
  },
  data(){
    return {
      form: {
        barcodeUrl: null
      },
      barcodeUrl:''
    }
  },
  // mounted() {
  //   this.getBarcode()
  // },
  methods: {
    getBarcode(){
      console.log("---------------------",this.barcodeType)
      this.form.barcodeUrl = null;
      if(this.bussinessCode == null){
        return ;
      }
      //二维码查询参数
      let barcodeParams = {
        bussinessId: this.bussinessId,
        bussinessCode: this.bussinessCode,
        barcodeFormart: this.barcodeFormart,
        barcodeType: this.barcodeType
      };
      getBarcodeUrl(barcodeParams).then( response =>{
        console.log(response,'59')
        if(response.data != null){
          //this.$set(this.form,'barcodeUrl',response.data.barcodeUrl);//强制刷新DOM
          // this.form.barcodeUrl = response.data.barcodeUrl;
          this.barcodeUrl = response.data.barcodeUrl;
          console.log(this.barcodeUrl,'63')
        }
      });
    },

  },
  watch: {
    'bussinessCode': 'getBarcode'
  }

}
</script>
<style scoped>
.barcodeClass {
  width: 200px;
  height: 200px;
  border: 1px dashed;
  position: relative;
  display: inline-block;
}

.flex-container{
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
</style>
