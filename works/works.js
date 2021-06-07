/*
    需要在 common.js 之后加载
 */


Vue.component('simbot-work', {
    props: [],
    data: function () {
        return {
            works: {

            }
        }
    }
})

/*
 * 作品集，通过add追加一个作品。
 */
const WORKS = {


    /**
     *
     * @param work
     */
    add(work) {
        console.log(work.name)
    }
}

deepFreeze(WORKS)


//
const WORK_SHOW = {
    /**
     * 作品的名称。可以简短一些。
     */
    name: String,

    /**
     * 作品的描述
     */
    description: String,

    /**
     * 作品所属，0：官方，1：第三方
     */
    belonging: Number,

    /**
     * 作品类型列表，不能为空
     */
    type: [],


    /**
     * 作品的首页。
     */
    home: {
        /** 作品首页的名称。 */
        name: String,
        /** 作品首页的链接。 */
        url: String
    },

    /**
     * 可以提供一个路径内的静态页面的路径来展示详细信息。
     */
    detailPage: String,


    /**
     * 作者信息
     */
    author: {
        /** 作者的名称 */
        name: String,
        /** 联系方式 */
        contact: String,
        /** 如果有的话，这是个QQ号，可用于快速添加好友以及显示QQ头像。 */
        qq: Number,
        /** 可以自己指定一个头像链接。 */
        avatar: String
    },




}


