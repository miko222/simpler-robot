/*
 *
 *  * Copyright (c) 2021. ForteScarlet All rights reserved.
 *  * Project  simple-robot
 *  * File     MiraiAvatar.kt
 *  *
 *  * You can contact the author through the following channels:
 *  * github https://github.com/ForteScarlet
 *  * gitee  https://gitee.com/ForteScarlet
 *  * email  ForteScarlet@163.com
 *  * QQ     1149159218
 *
 */

package love.forte.test

import love.forte.simbot.utils.ResourcePathExpression
import love.forte.simbot.utils.readToProperties
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.streams.toList
import kotlin.test.Test


/**
 *
 * @author ForteScarlet
 */
class ResourcePathExpressionUtilTest {

    @Test
    fun classpathExpression1() {
        val e = "classpath:bots/this.bot"

        val expression = ResourcePathExpression.getInstance(e)

        println(expression)
        println(expression.expression)

        val resource = expression.getResources()[0]

        println(resource.readToProperties())


    }

    @Test
    fun classpathExpression2() {
        val e = "resource:bots/this2.bot"

        val expression = ResourcePathExpression.getInstance(e)

        println(expression)
        println(expression.expression)

        val resource = expression.getResources()[0]

        println(resource.readToProperties())

    }

    @Test
    fun fileExpression1() {
        val file = "file:test.bot"

        val expression = ResourcePathExpression.getInstance(file)

        println(expression)
        println(expression.expression)
        println(expression.type)

        val resource = expression.getResources()[0]

        println(resource.name)

        val prop = resource.readToProperties()

    }


    @Test
    fun expression2FindTest() {
        val e = "bots/*.bot"

        val p = Path(".")

        // val ep = Path(e)

        val regex = Regex(e.replace(".", "\\.")
            .replace("*", "((?![/\\\\\\.;]).)+")
            .replace("**", "((?![\\.;]).)+")
        )

        println(regex)

        println(regex.matches("bots/forte.bot"))
        println(regex.matches("bots/and/forte.bot"))
        println(regex.matches("bots/forli.bot"))

        val l = Files.find(p, 3, { p, _ ->
            println(p)
            println(p.toRealPath())
            println()
            true
        }).toList()

        println("----")

        l.forEach { println(it) }

    }






}


private interface PathMatcher



public class PathFinder(expression: String) {

    private val expressionSplitList: List<String> = expression.split(Regex("[/\\\\]"))

    init {
        if (expressionSplitList.isEmpty()) {
            error("Expression has no path.")
        }
    }


    fun find(rootPath: Path): List<Path> {

        TODO()
    }

}
