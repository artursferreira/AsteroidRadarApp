package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.domain.Asteroid

class AsteroidClick(val block: (Asteroid) -> Unit) {
    /**
     * Called when a asteroid is clicked
     *
     * @param asteroid the asteroid that was clicked
     */
    fun onClick(asteroid: Asteroid) = block(asteroid)
}
