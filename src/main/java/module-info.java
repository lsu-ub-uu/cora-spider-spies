/**
 * The spider spies module provides common spies used when testing Cora projects.
 */
module se.uu.ub.cora.spider.spies {

	requires org.testng;
	requires transitive se.uu.ub.cora.data.spies;
	requires transitive se.uu.ub.cora.spider;

	requires transitive se.uu.ub.cora.testutils;

	exports se.uu.ub.cora.spider.spies;
	exports se.uu.ub.cora.spider.spies.binary.iiif;
}