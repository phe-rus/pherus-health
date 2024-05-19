package pherus.health.local

/**
@Dao
interface PatientInformationDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insert(patientEntity: PatientInformation)

@Delete
suspend fun delete(patientEntity: PatientInformation)

@Update
suspend fun update(patientEntity: PatientInformation)

@Query("SELECT * FROM patientInformation")
fun getPatientInformation(): Flow<List<PatientInformation>> // Change return type to List
} **/