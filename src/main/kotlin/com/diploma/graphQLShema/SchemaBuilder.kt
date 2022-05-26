package com.diploma.graphQLShema

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.diploma.databaseMutationController.*
import com.diploma.model.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.Exception

fun SchemaBuilder.schemaValue() {

//    init()

    mutation("userRegistration"){
        description = "Register a new user "
        resolver { userInput: UserData ->
            try{
                UserMutation().userRegistration(userInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("authUser"){
        description = "Authenticate user"
        resolver { email: String, password: String ->
            try{
                UserMutation().authUser(email, password)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("refreshToken"){
        description = "Refresh user token"
        resolver { userInput: UserData ->
            try{
                UserMutation().refreshUserToken(userInput)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("createUser") {
        description = "Create a new user"
        resolver { userInput: UserDataInput ->
            try {
                UserMutation().createUser(userInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateUser") {
        description = "Update user"
        resolver { id: Int, userInput: UserDataInput ->
            try{
                UserMutation().updateUser(id, userInput)
            true
        } catch (e: Exception) {
        false
    } }
    }

    mutation("deleteUser") {
        description = "Delete a user by his identifier"
        resolver { id: Int ->
            try {
                UserMutation().deleteUser(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllUsers") {
        description = "Показывает всех пользователей"
        resolver { ->
            transaction {
                User.selectAll().map{ User.toShowMap(it)}
            }
        }
    }

    query("showUserInfo") {
        description = "Показывает инфу текущего пользователя"
        resolver { id: Int ->
            transaction {
                User.select { User.id eq id }.map { User.toShowMap(it)}
            }
        }
    }

    mutation("createOrg") {
        description = "Create a new org"
        resolver { orgInput: OrganizationDataInput ->
            try {
                OrganizationMutation().createOrg(orgInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("deleteOrg") {
        description = "Delete org by it identifier"
        resolver { id: Int ->
            try {
                OrganizationMutation().deleteOrg(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllOrg") {
        description = "Показывает всех Org"
        resolver { ->
            transaction {
                Organization.selectAll().map{ Organization.toMap(it)}
            }
        }
    }

    mutation("createType") {
        description = "Create a new counter type"
        resolver { typeInput: TypeDataInput ->
            try {
                TypeMutation().createType(typeInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateType") {
        description = "Update Type"
        resolver {id: Int, typeInput: TypeDataInput ->
            try{
                TypeMutation().updateType(id, typeInput)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteType") {
        description = "Delete type by his identifier"
        resolver { id: Int ->
            try {
                TypeMutation().deleteType(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllTypes") {
        description = "Показывает все элементы таблицы Type"
        resolver { ->
            transaction {
                Type.selectAll().map{ Type.toMap(it)}
            }
        }
    }

    mutation("createPosition") {
        description = "Create a new Position"
        resolver { input: PositionData ->
            try {
                PositionMutation().createPosition(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updatePosition") {
        description = "Update Position"
        resolver {id: Int, input: PositionData ->
            try{
                PositionMutation().updatePosition(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deletePosition") {
        description = "Delete Position by his identifier"
        resolver { id: Int ->
            try {
                PositionMutation().deletePosition(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllPositions") {
        description = "Показывает все элементы таблицы Position"
        resolver { ->
            transaction {
                Position.selectAll().map{ Position.toMap(it)}
            }
        }
    }

    mutation("createMeasureReference") {
        description = "Create a new measure ref"
        resolver { input: MeasureReferenceDataInput ->
            try {
                MeasureReferenceMutation().createMeasureReference(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateMeasureReference") {
        description = "Update MeasureReference"
        resolver {id: Int, input: MeasureReferenceDataInput ->
            try{
                MeasureReferenceMutation().updateMeasureReference(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteMeasureReference") {
        description = "Delete measure ref by his identifier"
        resolver { id: Int ->
            try {
                MeasureReferenceMutation().deleteMeasureReference(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllMeasureReferences") {
        description = "Показывает все элементы таблицы MeasureReference"
        resolver { ->
            transaction {
                MeasureReference.selectAll().map{ MeasureReference.toMap(it)}
            }
        }
    }

    mutation("createApartment") {
        description = "Create a new Apartment"
        resolver { input: ApartmentDataInput ->
            try {
                ApartmentMutation().createApartment(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateApartment") {
        description = "Update Apartment"
        resolver {id: Int, input: ApartmentDataInput ->
            try{
                ApartmentMutation().updateApartment(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteApartment") {
        description = "Delete Apartment by his identifier"
        resolver { id: Int ->
            try {
                ApartmentMutation().deleteApartment(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllApartments") {
        description = "Показывает все элементы таблицы Apartment"
        resolver { ->
            transaction {
                Apartment.selectAll().map{ Apartment.toMap(it)}
            }
        }
    }

    mutation("createBranch") {
        description = "Create a new Branch"
        resolver { input: BranchDataInput ->
            try {
                BranchMutation().createBranch(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateBranch") {
        description = "Update Branch"
        resolver {id: Int, input: BranchDataInput ->
            try{
                BranchMutation().updateBranch(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteBranch") {
        description = "Delete Branch by his identifier"
        resolver { id: Int ->
            try {
                BranchMutation().deleteBranch(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllBranchrs") {
        description = "Показывает все элементы таблицы Branch"
        resolver { ->
            transaction {
                Branch.selectAll().map{ Branch.toMap(it)}
            }
        }
    }

    mutation("createCounterReference") {
        description = "Create a new CounterReference"
        resolver { input: CounterReferenceDataInput ->
            try {
                CounterReferenceMutation().createCounterReference(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateCounterReference") {
        description = "Update CounterReference"
        resolver {id: Int, input: CounterReferenceDataInput ->
            try{
                CounterReferenceMutation().updateCounterReference(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteCounterReference") {
        description = "Delete CounterReference by his identifier"
        resolver { id: Int ->
            try {
                CounterReferenceMutation().deleteCounterReference(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllCounterReferences") {
        description = "Показывает все элементы таблицы CounterReference"
        resolver { ->
            transaction {
                CounterReference.selectAll().map{ CounterReference.toMap(it)}
            }
        }
    }

    mutation("createEmployee") {
        description = "Create a new Employee"
        resolver { input: EmployeeDataInput ->
            try {
                EmployeeMutation().createEmployee(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateEmployee") {
        description = "Update Employee"
        resolver {id: Int, input: EmployeeDataInput ->
            try{
                EmployeeMutation().updateEmployee(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteEmployee") {
        description = "Delete Employee by his identifier"
        resolver { id: Int ->
            try {
                EmployeeMutation().deleteEmployee(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllEmployees") {
        description = "Показывает все элементы таблицы Employee"
        resolver { ->
            transaction {
                Employee.selectAll().map{ Employee.toMap(it)}
            }
        }
    }

    mutation("createPaymentHistory") {
        description = "Create a new PaymentHistory"
        resolver { input: PaymentHistoryDataInput ->
            try {
                PaymentHistoryMutation().createPaymentHistory(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updatePaymentHistory") {
        description = "Update PaymentHistory"
        resolver {id: Int, input: PaymentHistoryDataInput ->
            try{
                PaymentHistoryMutation().updatePaymentHistory(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deletePaymentHistory") {
        description = "Delete PaymentHistory by his identifier"
        resolver { id: Int ->
            try {
                PaymentHistoryMutation().deletePaymentHistory(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllPaymentHistories") {
        description = "Показывает все элементы таблицы PaymentHistory"
        resolver { ->
            transaction {
                PaymentHistory.selectAll().map{ PaymentHistory.toMap(it)}
            }
        }
    }

    mutation("createReadings") {
        description = "Create a new Readings"
        resolver { input: ReadingsDataInput ->
            try {
                ReadingsMutation().createReadings(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateReadings") {
        description = "Update Readings"
        resolver {id: Int, input: ReadingsDataInput ->
            try{
                ReadingsMutation().updateReadings(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteReadings") {
        description = "Delete Readings by his identifier"
        resolver { id: Int ->
            try {
                ReadingsMutation().deleteReadings(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllReadings") {
        description = "Показывает все элементы таблицы Readings"
        resolver { ->
            transaction {
                Readings.selectAll().map{ Readings.toMap(it)}
            }
        }
    }

    mutation("createService") {
        description = "Create a new Service"
        resolver { input: ServiceDataInput ->
            try {
                ServiceMutation().createService(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateService") {
        description = "Update Service"
        resolver {id: Int, input: ServiceDataInput ->
            try{
                ServiceMutation().updateService(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteService") {
        description = "Delete Service by his identifier"
        resolver { id: Int ->
            try {
                ServiceMutation().deleteService(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllServices") {
        description = "Показывает все элементы таблицы Service"
        resolver { ->
            transaction {
                Service.selectAll().map{ Service.toMap(it)}
            }
        }
    }

    mutation("createServiceRecord") {
        description = "Create a new ServiceRecord"
        resolver { input: ServiceRecordDataInput ->
            try {
                ServiceRecordMutation().createServiceRecord(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateServiceRecord") {
        description = "Update ServiceRecord"
        resolver {id: Int, input: ServiceRecordDataInput ->
            try{
                ServiceRecordMutation().updateServiceRecord(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteServiceRecord") {
        description = "Delete ServiceRecord by his identifier"
        resolver { id: Int ->
            try {
                ServiceRecordMutation().deleteServiceRecord(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllServiceRecords") {
        description = "Показывает все элементы таблицы ServiceRecord"
        resolver { ->
            transaction {
                ServiceRecord.selectAll().map{ ServiceRecord.toMap(it)}
            }
        }
    }

    type<TypeData>{
        description = "TypeData"
    }

    inputType<TypeDataInput>{
        description = "The input of the Types without the identifier"
    }

    type<ApartmentData>{
        description = "ApartmentData"
    }

    inputType<ApartmentDataInput>{
        description = "The input of the Apartments without the identifier"
    }

    type<BranchData>{
        description = "BranchData"
    }

    inputType<BranchDataInput>{
        description = "The input of the Branchs without the identifier"
    }

    type<CounterReferenceData>{
        description = "CounterReferenceData"
    }

    inputType<CounterReferenceDataInput>{
        description = "The input of the CounterReferences without the identifier"
    }

    type<EmployeeData>{
        description = "EmployeeData"
    }

    inputType<EmployeeDataInput>{
        description = "The input of the Employees without the identifier"
    }

    type<MeasureReferenceData>{
        description = "MeasureReferenceData"
    }

    inputType<MeasureReferenceDataInput>{
        description = "The input of the MeasureReferences without the identifier"
    }

    type<PaymentHistoryData>{
        description = "PaymentHistoryData"
    }

    inputType<PaymentHistoryDataInput>{
        description = "The input of the PaymentHistorys without the identifier"
    }

    type<PositionData>{
        description = "PositionData"
    }

    inputType<PositionDataInput>{
        description = "The input of the Positions without the identifier"
    }

    type<ReadingsData>{
        description = "ReadingsData"
    }

    inputType<ReadingsDataInput>{
        description = "The input of the Readingss without the identifier"
    }

    type<ServiceData>{
        description = "ServiceData"
    }

    inputType<ServiceDataInput>{
        description = "The input of the Services without the identifier"
    }

    type<ServiceRecordData>{
        description = "ServiceRecordData"
    }

    inputType<ServiceRecordDataInput>{
        description = "The input of the ServiceRecords without the identifier"
    }

    type<OrganizationData>{
        description = "OrgData"
    }

    inputType<OrganizationDataInput>{
        description = "The input of the orgs without the identifier"
    }

    type<UserData>{
        description = "UserData"
    }

    inputType<UserDataInput>{
        description = "The input of the users without the identifier"
    }


}


