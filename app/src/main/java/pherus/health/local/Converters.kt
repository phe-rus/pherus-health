package pherus.health.local

/**
class Converters {
@TypeConverter
fun fromBasicInformations(value: BasicInformations?): String? {
return Gson().toJson(value)
}

@TypeConverter
fun toBasicInformations(value: String?): BasicInformations? {
val type = object : TypeToken<BasicInformations>() {}.type
return Gson().fromJson(value, type)
}

@TypeConverter
fun toString(healthData: HealthHistoryProp?): String? {
// Implement logic to serialize the HealthData object into a JSON string
return Gson().toJson(healthData)
}

@TypeConverter
fun fromString(value: String?): List<String>? {
return value?.split(",")
}

@TypeConverter
fun listToString(list: List<String>?): String? {
return list?.joinToString(",")
}

@TypeConverter
fun fromDate(value: Long?): Date? {
return value?.let { Date(it) }
}

@TypeConverter
fun toDate(date: Date?): Long? {
return date?.time
}
}  **/