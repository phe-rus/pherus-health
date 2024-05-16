package pherus.health.services

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import pherus.health.R

data class StateModel(val enabled: Boolean, val label: String, val icon: Icon)

class PherusTiles : TileService() {
    private var counter = 0

    // Called when your app can update your tile.
    override fun onStartListening() {
        super.onStartListening()
        val state = getStateFromService()
        qsTile.label = state.label
        qsTile.contentDescription = state.label
        qsTile.state = if (state.enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.icon = state.icon
        qsTile.updateTile()
    }

    // Called when the user taps on your tile in an active or inactive state.
    override fun onClick() {
        super.onClick()
        counter++
        val newState = StateModel(
            enabled = counter % 2 == 0,
            label = "Clicked $counter times",
            icon = Icon.createWithResource(
                this,
                R.drawable.pherus
            )
        )
        updateTile(newState)
    }

    // Update tile with new state
    private fun updateTile(state: StateModel) {
        qsTile.label = state.label
        qsTile.contentDescription = state.label
        qsTile.state = if (state.enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.icon = state.icon
        qsTile.updateTile()
    }

    // Function to get state from service (replace with your actual implementation)
    private fun getStateFromService(): StateModel {
        return StateModel(
            enabled = false,
            label = "Pherus Health",
            icon = Icon.createWithResource(this, R.drawable.pherus)
        )
    }
}
