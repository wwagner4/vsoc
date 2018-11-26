package vsoc.ga.trainga.ga.common

import vsoc.ga.common.data.Data02

class ValuesByIndexCollectorData02 extends ValuesByIndexCollector [Data02] {

  override def sumBase(value1: Data02, value2: Data02): Data02 = Data02Ops.sumData(value1, value2)

  override def divideBase(value: Data02, divisor: Double): Data02 = Data02Ops.div(value, divisor)

  override def unitValue: Data02 = new Data02()
}
