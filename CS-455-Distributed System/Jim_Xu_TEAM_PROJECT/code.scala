import org.apache.spark.rdd.RDD
import java.io.File

def getListOfFiles(dir: String):List[File] = {
  val d = new File(dir)
  if (d.exists && d.isDirectory) {
    d.listFiles.filter(_.isFile).toList
  } else {
    List[File]()
  }
}
var a = 0;
var b = 0;
case class Stuent(name:String, start:Int, end:Int)
val sRDD: RDD[Stuent] = sc.emptyRDD[Stuent]
val files = getListOfFiles("/Users/Jim/Downloads/dataset/dataset/sensing/phonelock")
for(file <- files){
	val student = sc.textFile(file.getAbsolutePath())
	val rDD = student.map(s=>s.split(",")).filter(s=>s(0)!="start").map(
	    s=>Stuentfilename, s(0).toInt, s(1).toInt)
	)
	rDD.first()
	sRDD.union(rDD)
}