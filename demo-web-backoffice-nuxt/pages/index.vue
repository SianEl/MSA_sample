<template>
    <h1>메인 페이지</h1>
    <nuxt-link :to="{path: 'loginForm', query: {prevUrl: '/'}}"><button>로그인 페이지</button></nuxt-link>
</template>
<script lang="ts">
export default {
    layout: 'default'
}
</script>