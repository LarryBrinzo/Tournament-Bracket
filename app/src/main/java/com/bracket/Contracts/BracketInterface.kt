package com.bracket.Contracts

import com.bracket.Models.Annotations
import com.bracket.Models.Connections
import com.bracket.Models.Rows

interface BracketInterface {

    interface View {
        fun setRows()
        fun setAnnotations()
        fun setConnections()
    }

    interface Model {
        fun getRows(): List<Rows>
        fun getAnnotations(): List<Annotations>
        fun getConnections(): List<Connections>
    }

}