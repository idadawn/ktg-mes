<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'KTG-MES-Git',
  data() {
    return {
      url: 'https://gitee.com/kutangguo/ktg-mes'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>