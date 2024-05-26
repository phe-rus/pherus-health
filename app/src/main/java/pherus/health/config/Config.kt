package pherus.health.config

import androidx.annotation.Keep
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import pherus.health.BuildConfig
import pherus.health.R
import pherus.health.config.Config.MedicineForm.capsule
import pherus.health.config.Config.MedicineForm.gel
import pherus.health.config.Config.MedicineForm.patch
import pherus.health.config.Config.MedicineForm.pill
import pherus.health.config.Config.MedicineForm.syrup
import pherus.health.config.Config.MedicineForm.tablet
import pherus.health.config.Config.MedicineForm.transmiiter
import pherus.health.config.Config.MedicineForm.vial
import java.io.Serializable
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Config {
    enum class NoteType {
        NOTE,
        DIARY,
        TASK
    }

    enum class Recurrence {
        Daily,
        Weekly,
        Monthly
    }

    enum class TimesOfDay {
        Morning,
        Afternoon,
        Evening,
        Night
    }

    enum class MedicineForm {
        capsule,
        pill,
        vial,
        patch,
        transmiiter,
        gel,
        tablet,
        syrup
    }

    @Keep
    data class PatientInformation(
        val basicInformations: BasicInformations? = BasicInformations(),
        val contactInformation: ContactInformation? = ContactInformation(),
        val coreHealthHistory: CoreHealthHistory? = CoreHealthHistory(),
        val consentToPrivacyPolicy: ConsentToPrivacyPolicy? = ConsentToPrivacyPolicy(),
        val systemInformation: SystemInformation? = SystemInformation()
    ) : Serializable

    @Keep
    data class SystemInformation(
        val currentUserUid: String? = null,
        val anonymousMode: Boolean? = false,
        val loggedInDevices: MutableList<LoggedInDevice>? = null
    ) : Serializable

    @Keep
    data class LoggedInDevice(
        val deviceName: String? = null,
        val deviceModel: String? = null,
        val loggedInAt: String? = null,
        val loggedInFrom: String? = null
    )

    @Keep
    data class BasicInformations(
        val patientsId: String? = null,
        val preferedName: String? = null,
        val genderIdentity: String? = null,
        val avatarHolder: String? = null,
        val coverBackground: String? = null,
        val createdAt: String? = null
    ) : Serializable

    @Keep
    data class ContactInformation(
        val dateOfbirth: String? = null,
        val email: String? = null,
        val phoneNumber: String? = null,
        val localAddress: String? = null,
        val countryResident: String? = null,
    ) : Serializable

    @Keep
    data class CoreHealthHistory(
        val chronicDiseases: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val hereditaryDiseases: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val habitalDrugsOrAlcohol: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val sleepingPatterns: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val screenTime: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val socialHistory: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val bloodDonation: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val bloodPressure: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val participateInHealthResearch: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val virtualSupportSystem: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val followDiet: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val exerciseSchedules: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val mentalIllness: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val vaccinationRecords: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val supportSystem: MedicalHistoryRecords? = MedicalHistoryRecords(),
        val constentResearchPurpose: MedicalHistoryRecords? = MedicalHistoryRecords(),
    ) : Serializable

    @Keep
    data class MedicalHistoryRecords(
        val question: String? = null,
        val answer: String? = null
    ) : Serializable

    @Keep
    data class ConsentToPrivacyPolicy(
        val version: String? = BuildConfig.VERSION_NAME,
        val date: String? = null,
        val consentContext: String? = null,
        val consentAgreement: Boolean? = false
    ) : Serializable

    @Keep
    data class Note(
        val id: String,
        val title: String,
        val date: Long,
        val content: String,
        val type: NoteType,
        val color: String,
        val isPinned: Boolean
    ) : Serializable

    @Keep
    data class HealthModule(
        val name: String,
        val key: String,
        val enabled: Boolean,
        val description: String
    ) : Serializable

    @Keep
    data class ProfInformation(
        val title: String? = null,
        val value: String? = null,
        val keyMap: String? = null,
        val icon: ImageVector? = null
    ) : Serializable

    @Keep
    data class EditorProfileInfor(
        var title: String? = null,
        var keyMap: String? = null,
        var editor: Boolean? = false
    ) : Serializable

    @Keep
    data class BottomHelper(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val hasNotification: Boolean,
        val badgeCount: Int? = null
    ) : Serializable

    fun getRecurrenceList(): List<Recurrence> {
        val recurrenceList = mutableListOf<Recurrence>()
        recurrenceList.add(Recurrence.Daily)
        recurrenceList.add(Recurrence.Weekly)
        recurrenceList.add(Recurrence.Monthly)

        return recurrenceList
    }

    @Composable
    fun getMedicineIcons(): List<Int> {
        val medicineIcons = mutableListOf<Int>()
        medicineIcons.add(R.drawable.medicine_pill)
        medicineIcons.add(R.drawable.medicine_tablets)
        medicineIcons.add(R.drawable.medicine_vial)
        medicineIcons.add(R.drawable.medicine_patch)
        medicineIcons.add(R.drawable.medicine_transmitter)
        medicineIcons.add(R.drawable.medicine_gel)
        medicineIcons.add(R.drawable.medicine_syrup)

        return medicineIcons
    }

    fun returnMedicineForm(): List<String> {
        val medicineForm = mutableListOf<String>()
        medicineForm.add(pill.name)
        medicineForm.add(capsule.name)
        medicineForm.add(vial.name)
        medicineForm.add(patch.name)
        medicineForm.add(transmiiter.name)
        medicineForm.add(gel.name)
        medicineForm.add(tablet.name)
        medicineForm.add(syrup.name)

        return medicineForm
    }

    fun returnMedicineTime(): List<String> {
        val medicineTime = mutableListOf<String>()
        medicineTime.add(TimesOfDay.Morning.name)
        medicineTime.add(TimesOfDay.Afternoon.name)
        medicineTime.add(TimesOfDay.Evening.name)
        medicineTime.add(TimesOfDay.Night.name)

        return medicineTime
    }

    fun returnMedicineIcons(medicineForm: MedicineForm): Int {
        return when (medicineForm) {
            pill -> R.drawable.medicine_pill
            capsule -> R.drawable.medicine_tablets
            vial -> R.drawable.medicine_vial
            patch -> R.drawable.medicine_patch
            transmiiter -> R.drawable.medicine_transmitter
            gel -> R.drawable.medicine_gel
            tablet -> R.drawable.medicine_tablets
            syrup -> R.drawable.medicine_syrup
        }
    }

    fun Int.toMonthName(): String {
        return DateFormatSymbols().months[this]
    }

    fun Date.toFormattedString(): String {
        val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
        return simpleDateFormat.format(this)
    }

    @Keep
    data class MedicineProps(
        val medicineName: String? = null,
        val dosage: Int? = 0,
        val recurrence: List<Recurrence>? = emptyList(),
        val endDate: String,
        val timeOfDay: List<TimesOfDay>? = emptyList(),
        val medicineForm: MedicineForm? = null,
        val personalNotes: String? = null
    ) : Serializable

    @Keep
    data class CollectionProps(
        val name: String,
        val image: String,
        val enabled: Boolean,
    ) : Serializable
}