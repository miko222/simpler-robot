/*
    这是官方作品的展示区，也是示例的地方。

 */

const author = {
    name: 'ForteScarlet',
    email: 'ForteScarlet@163.com',
    qq: '1149159218',
    avatar: 'https://q1.qlogo.cn/g?b=qq&nk=1149159218&s=640'

}
deepFreeze(author)

// api
WORKS.add({
    id: 'simple-robot',
    name: 'simple-robot:api模块',
    logo: 'logo/logo-4@0,75x.png',
    description: 'simple-robot的API模块，为simbot框架提供标准接口规范。',
    detailPage: null,
    belonging: WORK_BELONGING.OFFICIAL.value,
    type: [WORK_TYPE.FRAME.value, WORK_TYPE.API.value],
    home: {
        name: 'simpler-robot',
        url: 'https://github.com/ForteScarlet/simpler-robot'
    },

    author: author
})

// core
WORKS.add({
    id: 'simple-robot',
    name: 'simple-robot:core模块',
    logo: 'logo/logo-4@0,75x.png',
    description: 'simple-robot的核心模块，是所有组件的基础模块。继承simple-robot:api模块并实现其大部分标准接口。',
    detailPage: null,
    belonging: WORK_BELONGING.OFFICIAL.value,
    type: [WORK_TYPE.FRAME.value, WORK_TYPE.CORE.value],
    home: {
        name: 'simpler-robot',
        url: 'https://github.com/ForteScarlet/simpler-robot'
    },

    author: author
})



