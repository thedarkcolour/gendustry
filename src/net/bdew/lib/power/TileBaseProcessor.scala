/*
 * Copyright (c) bdew, 2013 - 2017
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.power

import net.bdew.lib.data.DataSlotFloat
import net.bdew.lib.data.base.{TileDataSlotsTicking, UpdateKind}
import net.bdew.lib.machine.ProcessorMachine
import net.bdew.lib.tile.TileExtended
import net.bdew.lib.tile.inventory.{PersistentInventoryTile, SidedInventory}

abstract class TileBaseProcessor extends TileExtended
  with TileDataSlotsTicking
  with PersistentInventoryTile
  with SidedInventory
  with TilePoweredBase {
  def cfg: ProcessorMachine
  val power = DataSlotPower("power", this)
  val progress = DataSlotFloat("progress", this).setUpdate(UpdateKind.SAVE, UpdateKind.GUI)

  configurePower(cfg)

  serverTick listen { () =>
    if (power.stored > cfg.activationEnergy) {

      if (!isWorking)
        if (tryStart())
          progress := 0

      if (isWorking) {

        if ((progress < 1) && (power.stored > cfg.activationEnergy)) {
          val maxConsume = Math.min(Math.max(cfg.powerUseRate * power.stored, cfg.activationEnergy), cfg.mjPerItem * (1 - progress))
          val consumed = power.extract(maxConsume, false)
          progress += consumed / cfg.mjPerItem
        }

        if (progress >= 1) {
          if (tryFinish())
            progress := 0
        }
      }
    }
  }

  /**
    * Return true when an operation is in progress
    */
  def isWorking: Boolean

  /**
    * Try starting a new operation, return true if successful
    */
  def tryStart(): Boolean

  /**
    * Perform output when operation is done, return true if successful
    */
  def tryFinish(): Boolean
}
