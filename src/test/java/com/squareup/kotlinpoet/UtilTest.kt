/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.kotlinpoet

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals

import org.junit.Test

class UtilTest {
  @Test fun characterLiteral() {
    assertEquals("a", characterLiteralWithoutSingleQuotes('a'))
    assertEquals("b", characterLiteralWithoutSingleQuotes('b'))
    assertEquals("c", characterLiteralWithoutSingleQuotes('c'))
    assertEquals("%", characterLiteralWithoutSingleQuotes('%'))
    // common escapes
    assertEquals("\\b", characterLiteralWithoutSingleQuotes('\b'))
    assertEquals("\\t", characterLiteralWithoutSingleQuotes('\t'))
    assertEquals("\\n", characterLiteralWithoutSingleQuotes('\n'))
    assertEquals("\\u000c", characterLiteralWithoutSingleQuotes('\u000c'))
    assertEquals("\\r", characterLiteralWithoutSingleQuotes('\r'))
    assertEquals("\"", characterLiteralWithoutSingleQuotes('"'))
    assertEquals("\\'", characterLiteralWithoutSingleQuotes('\''))
    assertEquals("\\\\", characterLiteralWithoutSingleQuotes('\\'))
    // octal escapes
    assertEquals("\\u0000", characterLiteralWithoutSingleQuotes('\u0000'))
    assertEquals("\\u0007", characterLiteralWithoutSingleQuotes('\u0007'))
    assertEquals("?", characterLiteralWithoutSingleQuotes('\u003f'))
    assertEquals("\\u007f", characterLiteralWithoutSingleQuotes('\u007f'))
    assertEquals("¿", characterLiteralWithoutSingleQuotes('\u00bf'))
    assertEquals("ÿ", characterLiteralWithoutSingleQuotes('\u00ff'))
    // unicode escapes
    assertEquals("\\u0000", characterLiteralWithoutSingleQuotes('\u0000'))
    assertEquals("\\u0001", characterLiteralWithoutSingleQuotes('\u0001'))
    assertEquals("\\u0002", characterLiteralWithoutSingleQuotes('\u0002'))
    assertEquals("€", characterLiteralWithoutSingleQuotes('\u20AC'))
    assertEquals("☃", characterLiteralWithoutSingleQuotes('\u2603'))
    assertEquals("♠", characterLiteralWithoutSingleQuotes('\u2660'))
    assertEquals("♣", characterLiteralWithoutSingleQuotes('\u2663'))
    assertEquals("♥", characterLiteralWithoutSingleQuotes('\u2665'))
    assertEquals("♦", characterLiteralWithoutSingleQuotes('\u2666'))
    assertEquals("✵", characterLiteralWithoutSingleQuotes('\u2735'))
    assertEquals("✺", characterLiteralWithoutSingleQuotes('\u273A'))
    assertEquals("／", characterLiteralWithoutSingleQuotes('\uFF0F'))
  }

  @Test fun stringLiteral() {
    stringLiteral("abc")
    stringLiteral("♦♥♠♣")
    stringLiteral("€\\t@\\t$", "€\t@\t$")
    assertThat(stringLiteralWithQuotes("abc();\ndef();"))
        .isEqualTo("\"\"\"\n|abc();\n|def();\n\"\"\".trimMargin()")
    stringLiteral("This is \\\"quoted\\\"!", "This is \"quoted\"!")
    stringLiteral("e^{i\\\\pi}+1=0", "e^{i\\pi}+1=0")
  }

  @Test fun legalIdentifiers() {
    assertThat(isIdentifier("foo")).isTrue()
    assertThat(isIdentifier("bAr1")).isTrue()
    assertThat(isIdentifier("1")).isFalse()
    assertThat(isIdentifier("♦♥♠♣")).isFalse()
    assertThat(isIdentifier("`♦♥♠♣`")).isTrue()
    assertThat(isIdentifier("`  ♣ !`")).isTrue()
    assertThat(isIdentifier("€")).isFalse()
    assertThat(isIdentifier("`€`")).isTrue()
    assertThat(isIdentifier("`1`")).isTrue()
    assertThat(isIdentifier("```")).isFalse()
    assertThat(isIdentifier("``")).isFalse()
    assertThat(isIdentifier("\n")).isFalse()
    assertThat(isIdentifier("`\n`")).isFalse()
    assertThat(isIdentifier("\r")).isFalse()
    assertThat(isIdentifier("`\r`")).isFalse()
    assertThat(isIdentifier("when")).isTrue()
    assertThat(isIdentifier("fun")).isTrue()
    assertThat(isIdentifier("")).isFalse()
  }

  internal fun stringLiteral(string: String) = stringLiteral(string, string)

  internal fun stringLiteral(expected: String, value: String)
      = assertEquals("\"$expected\"", stringLiteralWithQuotes(value))
}
