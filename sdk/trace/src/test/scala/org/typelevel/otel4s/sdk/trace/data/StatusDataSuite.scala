/*
 * Copyright 2023 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s.sdk.trace.data

import cats.Show
import cats.kernel.laws.discipline.HashTests
import munit.DisciplineSuite
import org.scalacheck.Arbitrary
import org.scalacheck.Cogen
import org.scalacheck.Gen
import org.scalacheck.Prop
import org.typelevel.otel4s.trace.Status

class StatusDataSuite extends DisciplineSuite {

  private val statusGen: Gen[Status] =
    Gen.oneOf(Status.Ok, Status.Error, Status.Unset)

  private val statusDataGen: Gen[StatusData] =
    for {
      description <- Gen.alphaNumStr
      data <- Gen.oneOf(
        StatusData.Ok,
        StatusData.Unset,
        StatusData.Error(Some(description))
      )
    } yield data

  private implicit val statusDataArbitrary: Arbitrary[StatusData] =
    Arbitrary(statusDataGen)

  private implicit val statusCogen: Cogen[Status] =
    Cogen[String].contramap(_.toString)

  private implicit val statusDataCogen: Cogen[StatusData] =
    Cogen[(Status, Option[String])].contramap(s => (s.status, s.description))

  checkAll("StatusData.HashLaws", HashTests[StatusData].hash)

  test("Show[StatusData]") {
    Prop.forAll(statusDataGen) { data =>
      val expected = data match {
        case StatusData.Ok          => "StatusData{status=Ok}"
        case StatusData.Unset       => "StatusData{status=Unset}"
        case StatusData.Error(None) => "StatusData{status=Error}"
        case StatusData.Error(Some(description)) =>
          s"StatusData{status=Error, description=$description}"
      }

      assertEquals(Show[StatusData].show(data), expected)
    }
  }

  test("StatusData.Ok") {
    assertEquals(StatusData.Ok.status, Status.Ok)
    assertEquals(StatusData.Ok.description, None)
  }

  test("StatusData.Unset") {
    assertEquals(StatusData.Unset.status, Status.Unset)
    assertEquals(StatusData.Unset.description, None)
  }

  test("StatusData.Error") {
    Prop.forAll(Gen.alphaNumStr) { description =>
      val desc = Option.when(description.nonEmpty)(description)
      val error = StatusData.Error(desc)

      assertEquals(error.status, Status.Error)
      assertEquals(error.description, desc)
    }
  }

  test("create StatusData from a given status") {
    Prop.forAll(statusGen) { status =>
      val expected = status match {
        case Status.Ok    => StatusData.Ok
        case Status.Error => StatusData.Error(None)
        case Status.Unset => StatusData.Unset
      }

      assertEquals(StatusData(status), expected)
      assertEquals(StatusData(status, ""), expected)
    }
  }

}
