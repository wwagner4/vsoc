package vsoc.ga.genetic

trait Transformer[A, P <: Pheno[A]] {

  def toPheno(geno: Seq[A]): P

}