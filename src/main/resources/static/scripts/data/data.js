/**
 * Holds the campaign data coming from the API
 *
 * @param data Data retrieved from the API
 * @constructor
 */
let CampaignsData = function (data) {
    this.data = data;
};

/**
 * Returns the data field
 * @returns {*}
 */
CampaignsData.prototype.get = function () {
    return this.data;
};