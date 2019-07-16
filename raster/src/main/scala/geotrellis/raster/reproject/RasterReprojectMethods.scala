/*
 * Copyright 2016 Azavea
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

package geotrellis.raster.reproject

import geotrellis.proj4._
import geotrellis.raster._
import geotrellis.raster.resample.{ResampleGrid, TargetRegion}
import geotrellis.util.MethodExtensions

import spire.math.Integral

trait RasterReprojectMethods[+T <: Raster[_]] extends MethodExtensions[T] {

  def reproject[N: Integral](transform: Transform, inverseTransform: Transform, resampleGrid: ResampleGrid[N]): T

  def reproject[N: Integral](src: CRS, dest: CRS, resampleGrid: ResampleGrid[N]): T = {
    val transform = Transform(src, dest)
    val inverseTransform = Transform(dest, src)

    reproject(transform, inverseTransform, resampleGrid)
  }

  def reproject(src: CRS, dest: CRS): T =
    reproject(src, dest, TargetRegion(self.rasterExtent.toGridType[Int]))

}
