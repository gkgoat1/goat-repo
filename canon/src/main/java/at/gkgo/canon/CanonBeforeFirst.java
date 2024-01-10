package at.gkgo.canon;

import at.gkgo.canon.material.BeforeFirstMaterialEntrypoint;

public class CanonBeforeFirst implements BeforeFirstMaterialEntrypoint {
    @Override
    public void run() {
Canon.LOGGER.info("Starting common/compat before first entrypoint");
    }
}
