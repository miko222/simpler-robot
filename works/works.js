



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


const WORK_SHOW = {
    /**
     * 作品的名称。可以简短一些。
     * @type {String}
     */
    name: String,

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



