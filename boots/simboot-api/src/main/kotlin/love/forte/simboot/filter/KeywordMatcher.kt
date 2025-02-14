/*
 *  Copyright (c) 2021-2022 ForteScarlet <ForteScarlet@163.com>
 *
 *  本文件是 simply-robot (或称 simple-robot 3.x 、simbot 3.x ) 的一部分。
 *
 *  simply-robot 是自由软件：你可以再分发之和/或依照由自由软件基金会发布的 GNU 通用公共许可证修改之，无论是版本 3 许可证，还是（按你的决定）任何以后版都可以。
 *
 *  发布 simply-robot 是希望它能有用，但是并无保障;甚至连可销售和符合某个特定的目的都不保证。请参看 GNU 通用公共许可证，了解详情。
 *
 *  你应该随程序获得一份 GNU 通用公共许可证的复本。如果没有，请看:
 *  https://www.gnu.org/licenses
 *  https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *  https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *
 *
 */

package love.forte.simboot.filter

import love.forte.simboot.Matcher

/**
 * 使用 [Keyword] 作为匹配规则的匹配器。
 * @author ForteScarlet
 */
public fun interface KeywordMatcher : Matcher<String, Keyword>


/**
 * 使用 keyword 进行的正则匹配.
 *
 * 如果你想使用 [StringMatcher] 作为 [KeywordMatcher] 使用，参考 [asKeywordMatcher].
 *
 */
public enum class KeywordRegexMatchers(private val matcher: KeywordMatcher) :
    KeywordMatcher by matcher {
    /**
     * 完整正则匹配
     */
    MATCHES({ t, r -> r.regex.matches(t) }),

    /**
     * 包含正则匹配
     */
    CONTAINS({ t, r -> r.regex.containsMatchIn(t) }),

}


public fun StringMatcher.toKeywordMatcher(): KeywordMatcher = KeywordMatcher { t: String, r: Keyword -> match(t, r.text) }
