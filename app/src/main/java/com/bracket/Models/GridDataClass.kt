package com.bracket.Models

data class GridDataClass(var c: C, var defaultCompetitionID: String="")

data class C(var finalsGrids: List<FinalsGrids>, var defaultCompetitionID: String="")

data class FinalsGrids(var rows: List<Rows>, var annotations: List<Annotations>, var connections: List<Connections>,
                       var competitionID: String="", var competitionName: String="")

data class Rows(var elementID: String="", var items: List<List<Items>>)

data class Items(var elementID: String="", var style: String="", var leftTeamID: String="",
                 var rightTeamID: String="", var name: String="", var trophyRemoteImageURL: String="")

data class Annotations(var text: String="", var elementID: String="", var alignment: String="", var edge: String="")

data class Connections(var fromElementID: String="", var toElementIDs: List<String>)