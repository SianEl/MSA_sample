<template>
   <main>
    <h1>백 오피스</h1>
    <nuxt-link :to="{name: 'loginForm'}"><button type="button">로그인 페이지</button></nuxt-link>
    <nuxt-link :to="{name: 'auth'}"><button type="button">마이 페이지</button></nuxt-link>
  </main>
</template>
<script>
  export default {
    name: 'main',
    methods: {
      pageLink(path) {
        
      }
    }
  }
</script>