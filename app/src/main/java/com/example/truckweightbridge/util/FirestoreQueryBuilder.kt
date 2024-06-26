package com.example.truckweightbridge.util

class FirestoreQueryBuilder(private val collectionId: String="truckweightbridge") {

    private var where: Where? = null
    private var orderBy: List<OrderBy>? = null
    private var limit: Int? = null

//    private val fieldFilters = mutableListOf<FieldFilter>()
    private val filters = mutableListOf<Filter>()
    fun where(field: String, op: String, value: Any): FirestoreQueryBuilder {
        val fieldValue = when (value) {
            is Int -> Value(integerValue = value)
            is String -> Value(stringValue = value)
            else -> throw IllegalArgumentException("Unsupported value type")
        }

        val fieldFilter = FieldFilter(Field(field), op, fieldValue)
        val filter = Filter(fieldFilter)
        filters.add(filter)
//        fieldFilters.add(fieldFilter)
        return this
    }


    fun orderBy(field: String, direction: String): FirestoreQueryBuilder {
        val order = OrderBy(Field(field), direction)
        this.orderBy = listOf(order)
        return this
    }

    fun limit(limit: Int): FirestoreQueryBuilder {
        this.limit = limit
        return this
    }

    fun build(): FirestoreQueryRequest {
        val from = listOf(From(collectionId))
        val where  = if (filters.isNotEmpty()){
            if (filters.size == 1) {
                Where(fieldFilter = filters[0].fieldFilter)
            } else {
                Where(CompositeFilter("AND", filters))
            }
        }else{
            null
        }

        val structuredQuery = StructuredQuery(from, where, orderBy, limit)
        return FirestoreQueryRequest(structuredQuery)
    }
}
