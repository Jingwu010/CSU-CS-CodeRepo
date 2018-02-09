~~~~scala
// get the uid from the file name

val name: String => String = _.split('_')(1).split('.')(0)
val extractUid = udf(name)
~~~~



~~~~scala
// build dataframe from Activity_json
// Working Hours and Relaxing Hours
case class Activity_json(uid: String, a_Wo: Double, s_Wo: Double, a_Re: Double, s_Re: Double)
val activity_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Activity")
.select(input_file_name, $"working", $"relaxing")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("working"), stddev_samp("working"), avg("relaxing"), stddev_samp("relaxing"))
.withColumnRenamed("avg(working)", "a_Wo")
.withColumnRenamed("stddev_samp(working)", "s_Wo")
.withColumnRenamed("avg(relaxing)", "a_Re")
.withColumnRenamed("stddev_samp(relaxing)", "s_Re")
.as[Activity_json]
activity_json.cache
~~~~



~~~~scala
// build dataframe from Class_json
// Hours and Experience for Class
case class Class_json(uid: String, a_Ho: Double, s_Ho: Double, v_Ho: Double, a_Exp: Double,  s_Exp: Double, v_Exp: Double)
val class_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Class")
.select(input_file_name, $"hours", $"experience")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("hours"), stddev_samp("hours"), var_samp("hours"), avg("experience"), stddev_samp("experience"), var_samp("experience"))
.withColumnRenamed("avg(hours)", "a_Ho")
.withColumnRenamed("stddev_samp(hours)", "s_Ho")
.withColumnRenamed("var_samp(hours)", "v_Ho")
.withColumnRenamed("avg(experience)", "a_Exp")
.withColumnRenamed("stddev_samp(experience)", "s_Exp")
.withColumnRenamed("var_samp(experience)", "v_Exp")
.as[Class_json]
class_json.cache
~~~~



~~~~scala
// build dataframe from Class2_json
// Effort and Expected Grade
case class Class2_json(uid: String, a_Ef: Double, s_Gr: Double)
val class2_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Class 2")
.select(input_file_name, $"effort", $"grade")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("effort"), avg("grade"))
.withColumnRenamed("avg(effort)", "a_Ef")
.withColumnRenamed("avg(grade)", "s_Gr")
.as[Class2_json]
class2_json.cache
~~~~



~~~~scala
// build dataframe from Exercise_json
// Exercise Hours and Exercise Days and Walk Hours
case class Exercise_json(uid: String, a_Ex: Double, s_Ha: Double, a_Wa: Double)
val exercise_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Exercise")
.select(input_file_name, $"exercise", $"have", $"walk")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("exercise"), sum("have"), avg("walk"))
.withColumnRenamed("avg(exercise)", "a_Ex")
.withColumnRenamed("sum(have)", "s_Ha")
.withColumnRenamed("avg(walk)", "a_Wa")
.as[Exercise_json]
exercise_json.cache
~~~~



~~~~Scala
// build dataframe from Lab_json
// Lab Hours
case class Lab_json(uid: String, a_Du: Double)
val lab_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Lab")
.select(input_file_name, $"duration")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("duration"))
.withColumnRenamed("avg(duration)", "a_Du")
.as[Lab_json]
lab_json.cache
~~~~



~~~~scala
// build dataframe from Sleep_json
// Sleep Hours and Sleep rate
case class Sleep_json(uid: String, a_Hr: Double, a_Ra: Double)
val sleep_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Sleep")
.select(input_file_name, $"hour", $"rate")
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("hour"), avg("rate"))
.withColumnRenamed("avg(hour)", "a_Hr")
.withColumnRenamed("avg(rate)", "a_Ra")
.as[Sleep_json]
sleep_json.cache
~~~~



~~~~scala
// build dataframe from Stress_json
// Stress Level and Maximum Stree Level
case class Stress_json(uid: String, a_Lv: Double, m_Lv: Double)
val stress_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Stress")
.select(input_file_name, $"level".cast("Double"))
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("level"), max("level"))
.withColumnRenamed("avg(level)", "a_Lv")
.withColumnRenamed("max(level)", "m_Lv")
.as[Stress_json]
stress_json.cache
~~~~



~~~~scala
// build dataframe from Stress_json
// Noise Level and Productivity
case class Study_space_json(uid: String, a_No: Double, a_Pr: Double, m_No: Double, m_Pr: Double)
val study_space_json = spark
.read
.json("/Users/Jim/Downloads/dataset/dataset/EMA/response/Study Spaces")
.select(input_file_name, $"noise".cast("Double"), $"productivity".cast("Double"))
.withColumn("input_file_name()", getName($"input_file_name()"))
.withColumnRenamed("input_file_name()", "uid")
.groupBy("uid")
.agg(avg("noise"), avg("productivity"), max("noise"), max("productivity"))
.withColumnRenamed("avg(noise)", "a_No")
.withColumnRenamed("avg(productivity)", "a_Pr")
.withColumnRenamed("max(noise)", "m_No")
.withColumnRenamed("max(productivity)", "m_Pr")
.as[Study_space_json]
study_space_json.cache
~~~~



~~~~scala
val EMA = activity_json
.join(class_json, Seq("uid"))
.join(class2_json, Seq("uid"))
.join(exercise_json, Seq("uid"))
.join(lab_json, Seq("uid"))
.join(sleep_json, Seq("uid"))
.join(stress_json, Seq("uid"))
.join(study_space_json, Seq("uid"))
~~~~



~~~~scala
// build grades from Education
val grades = spark.read
.option("header", true)
.option("inferSchema", true)
.csv("/Users/Jim/Downloads/dataset/dataset/education/grades.csv")
.select($"uid" , $" gpa all", $" gpa 13s", $" cs 65")
.withColumnRenamed(" gpa all", "GPA")
.cache
~~~~



~~~~scala
val EMA = activity_json
.join(class_json, Seq("uid"))
.join(class2_json, Seq("uid"))
.join(exercise_json, Seq("uid"))
.join(lab_json, Seq("uid"))
.join(sleep_json, Seq("uid"))
.join(stress_json, Seq("uid"))
.join(study_space_json, Seq("uid"))
~~~~



~~~~scala
val all = grades.join(EMA, Seq("uid")).na.drop
~~~~



~~~~scala
import org.apache.spark.ml.feature.VectorAssembler

val assembler = new VectorAssembler()
.setInputCols(all.columns.filter((item) => item != "uid" && item != "GPA"))
.setOutputCol("features")

val transformed = assembler.transform(all)
~~~~



~~~~scala
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.ml.regression.RandomForestRegressionModel

val Array(trainingData, testData) = transformed.randomSplit(Array(0.7, 0.3))

val rf = new RandomForestRegressor()
.setLabelCol("GPA")
.setFeaturesCol("features")

val model = rf.fit(trainingData)

val predictions = model.transform(testData)

import org.apache.spark.ml.evaluation.RegressionEvaluator
val evaluator = new RegressionEvaluator()
  .setLabelCol("GPA")
  .setPredictionCol("prediction")
  .setMetricName("rmse")
val rmse = evaluator.evaluate(predictions)
println("Root Mean Squared Error (RMSE) on test data = " + rmse)
println("Learned regression tree model:\n" + model.toDebugString)
~~~~



~~~~scala
predictions.select( $"GPA", $"prediction").show
~~~~

