package com.ciptakerjaarunika.kerjaloka.model.Data

data class Skill(
    val skillName : String,
    val skillNo : Int,
)

data class SkillFilter(
    val skillName : String,
    val skillNo : Int,
    var checked : Boolean?
)

