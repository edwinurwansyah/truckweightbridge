package com.example.truckweightbridge.util


data class FirestoreQueryRequest(
    val structuredQuery: StructuredQuery
)

data class StructuredQuery(
    val from: List<From>,
    val where: Where? = null,
    val orderBy: List<OrderBy>? = null,
    val limit: Int? = null
)

data class From(
    val collectionId: String
)

data class Where(
    val compositeFilter: CompositeFilter
)

data class CompositeFilter(
    val op: String,
    val filters: List<Filter>
)

data class Filter(
    val fieldFilter: FieldFilter
)

data class FieldFilter(
    val field: Field,
    val op: String,
    val value: Value
)

data class Field(
    val fieldPath: String
)

data class Value(
    val integerValue: Int? = null,
    val stringValue: String? = null
)

data class OrderBy(
    val field: Field,
    val direction: String
)

data class FirestoreDocumentResponse(
    val document: FirestoreDocument
)

data class FirestoreDocument(
    val name: String,
    val fields: Fields
)

data class Fields(
    val id: IntegerValue?,
    val dateTime: StringValue?,
    val netWeight: IntegerValue?,
    val outboundWeight: IntegerValue?,
    val inboundWeight: IntegerValue?,
    val licenseNumber: IntegerValue?,
    val driverName: StringValue?
)

data class IntegerValue(
    val integerValue: String
)

data class StringValue(
    val stringValue: String
)


data class FirestoreField(
    val stringValue: String? = null,
    val integerValue: String? = null
)