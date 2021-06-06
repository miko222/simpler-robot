/*
    请不要嘲笑我拙劣的前端技艺，感谢 😔

 */


/**
 * 作品所属类型。
 * @type {{OFFICIAL: {name: string, value: number}, THIRD_PARTY: {name: string, value: number}}}
 */
const WORK_BELONGING = {
    /**
     * 作品所属: 官方
     * 官方所属仅允许官方人员使用。
     */
    OFFICIAL: {
        value: 0,
        name: "官方"
    },
    /**
     * 作品所属：第三方。
     */
    THIRD_PARTY: {
        value: 1,
        name: "第三方"
    }
}

deepFreeze(WORK_BELONGING)
// Object.freeze()
// Object.freeze(WORK_BELONGING.OFFICIAL)
// Object.freeze(WORK_BELONGING.THIRD_PARTY)

/**
 * 作品类型。作品类型是可以多选的。
 * @type {{CORE: {name: string, value: number}, APP: {name: string, value: number}, API: {name: string, value: number}, COMPONENT: {name: string, value: number}, AUXILIARY: {name: string, value: number}, FRAME: {name: string, value: number}}}
 */
const WORK_TYPE = {
    /**
     * 作品类型：核心
     *
     * 核心一般指simbot核心模块自身。
     */
    CORE: {
        value: 0,
        name: "核心"
    },

    /**
     * 作品类型：API
     *
     * API一般是提供某一平台的API支持而不提供对接。
     * 大多数情况下的项目可能会包含API类型，但不会仅仅只有API。
     */
    API: {
        value: 1,
        name: "API"
    },

    /**
     * 作品类型：组件
     *
     * 组件一般代表为对接某个bot平台的组件
     */
    COMPONENT: {
        value: 2,
        name: "组件"
    },

    /**
     * 作品类型：辅助性
     *
     * 辅助性类型一般指用于辅助simbot下项目的开发相关。
     */
    AUXILIARY: {
        value: 3,
        name: "辅助性"
    },


    /**
     * 作品类型：框架
     *
     * 一般指基于simbot二次开发的第三方框架
     */
    FRAME: {
        value: 4,
        name: "框架"
    },

    /**
     * 作品类型：应用
     *
     * 这应该是最常见的类型，包括了是个人自用的，还是开放使用的应用。
     *
     */
    APP: {
        value: 5,
        name: "应用"
    }
}

deepFreeze(WORK_TYPE)


/**
 * 此作品的面向目标。一般就是代表是自用还是对公。
 *
 * @type {{PUBLIC: {name: string, value: number}, SELF: {name: string, value: number}}}
 */
const TARGET_ORIENTED = {
    SELF: {
        value: 0,
        name: "自用"
    },
    PUBLIC: {
        value: 1,
        name: "对公"
    }
}

deepFreeze(TARGET_ORIENTED)



function deepFreeze(obj) {
    const propNames = Object.getOwnPropertyNames(obj);
    propNames.forEach(function (name) {
        const prop = obj[name];
        if (typeof prop == 'object' && prop !== null) {
            deepFreeze(prop);
        }
    });
    return Object.freeze(obj);
}


