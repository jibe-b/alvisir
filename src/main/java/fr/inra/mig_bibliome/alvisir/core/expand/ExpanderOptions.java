package fr.inra.mig_bibliome.alvisir.core.expand;

import fr.inra.mig_bibliome.alvisir.core.index.NormalizationOptions;

/**
 * Options to control the expansion of queries.
 * @author rbossy
 *
 */
public interface ExpanderOptions {
	/**
	 * Returns the normalization options for the specified field.
	 * @param fieldName
	 * @return the normalization options for the specified field
	 */
	NormalizationOptions getNormalizationOptions(String fieldName);
}
