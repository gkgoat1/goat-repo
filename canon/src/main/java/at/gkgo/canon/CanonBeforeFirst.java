package at.gkgo.canon;

import at.gkgo.canon.material.BeforeFirstMaterialEntrypoint;
import at.gkgo.canon.meta.BeforeFirstMetaEntrypoint;

public class CanonBeforeFirst implements BeforeFirstMaterialEntrypoint, BeforeFirstMetaEntrypoint {
    @Override
    public void runBeforeMaterials() {
Canon.LOGGER.info("Starting common/compat before first material entrypoint");
    }

    @Override
    public void runBeforeMetas() {
        Canon.LOGGER.info("Starting common/compat before first meta entrypoint");
    }
}
