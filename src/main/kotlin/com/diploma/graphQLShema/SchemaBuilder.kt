package com.diploma.graphQLShema

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.diploma.databaseMutationController.*
import com.diploma.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.Exception

fun SchemaBuilder.schemaValue() {

//    init()

    mutation("userRegistration"){
        description = "Register a new user "
        resolver { userInput: UserDataInput ->
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
        resolver { userInput: UserDataInput ->
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

    query("showUserInfo") {
        description = "Показывает информацию о пользователе по параметрам или всех пользователей"
        resolver { id: Int?, name: String?, email: String?, phoneNumber: String?, birthDate: String?, address: String?, orgId: Int? ->
            transaction {
                UserMutation().showUser(id, name, email, phoneNumber, birthDate, address, orgId)
            }
        }
    }

    mutation("createUserApartment") {
        description = "Create a new user-apartment relationship"
        resolver { userInput: UserApartmentDataInput ->
            try {
                UserMutation().addApartmentToUser(userInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("deleteUser") {
        description = "Delete a user-apartment by his identifier"
        resolver { id: Int ->
            try {
                UserMutation().deleteApartmentToUser(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showUserApartment") {
        description = "Показывает отношения таблиц пользователя и помещения по параметрам "
        resolver { id: Int?, userId: Int?, apartmentId: Int? ->
            transaction {
                UserMutation().showUserApartment(id, userId, apartmentId)
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

    query("showOrg") {
        description = "Показывает Организацию по:" +
                "1. Id" +
                "2. Названию"
        resolver { id: Int?, name: String? ->
            transaction {
                OrganizationMutation().showOrg(id, name)
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

    query("showType") {
        description = "Выводит тип счетчика по:" +
                "1. Id" +
                "2. Названию" +
                "3. Все, если не было введено данных"
        resolver { id: Int?, name: String? ->
            transaction {
                TypeMutation().showType(id, name)
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

    query("showPosition") {
        description = "Показывает должности по:" +
                "1. Id" +
                "2. Названию" +
                "3. Все, если не введены переменные"
        resolver { id: Int?, name: String? ->
            transaction {
                PositionMutation().showPosition(id, name)
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

    query("showMeasureReference") {
        description = "Показывает все элементы таблицы MeasureReference, если не введены переменные или по:" +
                "1. Id" +
                "2. Полному названию" +
                "3. Сокращенному названию"
        resolver { id: Int?, fullName: String?, shortName: String? ->
            transaction {
                MeasureReferenceMutation().showMeasureReference(id, fullName, shortName)
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

    query("showApartment") {
        description = "Показывает все элементы таблицы Apartment или ищет по параметрам"
        resolver { id: Int?, fullSize: Int?, liveSize: Int?, category: String?, branchId: Int?, personalAccount: Int? ->
            transaction {
                ApartmentMutation().showApartment(id, fullSize, liveSize, category, branchId, personalAccount)
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

    query("showBranch") {
        description = "Показывает все элементы таблицы Branch"
        resolver { id: Int?, name: String?, country: String?, city: String?, address: String?, orgId: Int? ->
            transaction {
                BranchMutation().showBranch(id, name, country, city, address, orgId)
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
        description = "Показывает все элементы таблицы CounterReference или ищет по переменной"
        resolver { id: Int?, number: String?, model: String?, label: String?, serviceDate: String?, typeId: Int? ->
            transaction {
                CounterReferenceMutation().showCounterReference(id, number, model, label, serviceDate, typeId)
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

    query("showEmployee") {
        description = "Показывает сотрудника по:" +
                "1. Id" +
                "2. ФИО" +
                "3. Id филиала" +
                "4. Id должности" +
                "5. Всех, если не введены данные"
        resolver { id: Int?, name: String?, branchId: Int?, positionId: Int? ->
            transaction {
                EmployeeMutation().showEmployee(id, name, branchId, positionId)
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

    query("showReadings") {
        description = "Показывает показания счетчиков по:" +
                "1. id" +
                "2. id помещения" +
                "3. id Counter Reference" +
                "4. Все, если не вводить данных"
        resolver { id: Int?, apartmentId: Int?, counterRefId: Int? ->
            transaction {
                ReadingsMutation().showReadings(id, apartmentId, counterRefId)
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

    query("showService") {
        description = "Показывает услугу: " +
                "1. Определенную услугу по ID" +
                "2. Определенной категории по ID категории" +
                "3. Определенной должности по ID должности" +
                "4. С определенным названием" +
                "5. Все, если не вводить данные"
        resolver {name: String?, serviceId: Int?, categoryId: Int?, positionId: Int? ->
            transaction { ServiceMutation().showService(name, serviceId, categoryId, positionId) }
        }
    }

    query("showServicePrice") {
        description = "Показывает цену услуги"
        resolver { branchId: Int, serviceId: Int ->
            transaction {
                Service_Collection.select{(Service_Collection.serviceId eq serviceId) and (Service_Collection.branchId eq branchId)}.map { Service_Collection.toMap(it) }
            }
        }
    }

    mutation("createServiceCollection") {
        description = "Create a new ServiceCollection"
        resolver { input: ServiceCollectionDataInput ->
            try {
                ServiceCollectionMutation().createServiceCollection(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateServiceCollection") {
        description = "Update ServiceCollection"
        resolver {id: Int, input: ServiceCollectionDataInput ->
            try{
                ServiceCollectionMutation().updateServiceCollection(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteServiceCollection") {
        description = "Delete ServiceCollection by his identifier"
        resolver { id: Int ->
            try {
                ServiceCollectionMutation().deleteServiceCollection(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showServiceCollection") {
        description = "Показывает коллекцию услуг: " +
                "1. Коллекцию по ID филиала" +
                "2. Коллекцию по ID услуги" +
                "3. Коллекцию с диапазоном цен от cost1 до cost2" +
                "4. Коллекцию с ценой больше cost1" +
                "5. Коллекцию с ценой меньше cost2" +
                "6. Если введены все данные то поиск идет по всем вышенаписанным фильтрам" +
                "7. Все, если не вводить данные"
        resolver {branchId: Int?, serviceId: Int?, cost1: Float?, cost2: Float?  ->
            transaction { ServiceCollectionMutation().showServiceCollection(branchId, serviceId, cost1, cost2) }
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

    query("showServiceRecord") {
        description = "Показывает все элементы таблицы ServiceRecord или поиск по параметру"
        resolver { id: Int?, registrationDate: String?, status: String?, userId: Int?, serviceId: Int?, employeeId: Int? ->
            transaction {
                ServiceRecordMutation().showServiceRecord(id, registrationDate, status, userId, serviceId, employeeId)
            }
        }
    }

    mutation("createCategory") {
        description = "Create a new Category"
        resolver { input: CategoryData ->
            try {
                CategoryMutation().createCategory(input)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateCategory") {
        description = "Update Category"
        resolver {id: Int, input: CategoryData ->
            try{
                CategoryMutation().updateCategory(id, input)
                true
            } catch (e: Exception) {
                false
            } }
    }

    mutation("deleteCategory") {
        description = "Delete Category by his identifier"
        resolver { id: Int ->
            try {
                CategoryMutation().deleteCategory(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showCategory") {
        description = "Показывает категории по:" +
                "1. Id" +
                "2. Имени категории" +
                "3. Все, если не введены данные"
        resolver { id: Int?, name: String? ->
            transaction {
                CategoryMutation().showCategory(id, name)
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
        description = "The input of the Branches without the identifier"
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
        description = "The input of the PaymentHistories without the identifier"
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
        description = "The input of the Readings without the identifier"
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

    type<CategoryData>{
        description = "CategoryData"
    }

    inputType<CategoryDataInput>{
        description = "The input of the Categories without the identifier"
    }


}


