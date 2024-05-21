package pherus.health.config

import androidx.annotation.Keep
import androidx.compose.ui.graphics.vector.ImageVector
import pherus.health.BuildConfig
import java.io.Serializable

object Config {
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
}