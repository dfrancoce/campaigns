/**
 * Manages all the operations related to the View
 *
 * @param campaignsData Data with all the campaigns stored in the database
 * @constructor
 */
let CampaignsView = function (campaignsData) {
    this.campaignsData = campaignsData;
};

/**
 * Initializes the campaigns view events
 */
CampaignsView.prototype.initEvents = function () {
    const self = this;

    self.initializeDataTable();
    self.onClickInDataTableRow();
};

/**
 * Initializes the campaigns DataTable and populates it with the
 * campaigns data
 */
CampaignsView.prototype.initializeDataTable = function () {
    const self = this;
    const campaignsDataTable = $('#campaignsDataTable');

    self.campaignsData.get().forEach(function (campaign) {
        campaignsDataTable.find('tbody:last').append('' +
            '<tr>' +
            '<td>' + campaign.name + '</td>' +
            '<td>' + campaign.goal + '</td>' +
            '<td>' + campaign.total_budget + '</td>' +
            '<td>' + campaign.status + '</td>' +
            '</tr>');
    });

    campaignsDataTable.DataTable({
        columnDefs: [
            {
                targets: [0, 1, 2],
                className: 'mdl-data-table__cell--non-numeric'
            }
        ]
    });
};

/**
 * Handles the actions when the user clicks a row of the DataTable
 */
CampaignsView.prototype.onClickInDataTableRow = function () {
    const self = this;
    const campaignsDataTable = $('#campaignsDataTable');

    $('#campaignsDataTable tbody').on('click', 'tr', function () {
        // Gets the data correspondent to the selected row
        const data = campaignsDataTable.DataTable().row(this).data();
        const selectedCampaignData = self.campaignsData.get().find(({name}) => name === data[0]);

        const facebook = $('#facebook');
        const instagram = $('#instagram');
        const google = $('#google');

        const template = self.getDetailViewTemplate();
        self.cleanDetailView(facebook, instagram, google);

        self.setFacebookDetailView(template, selectedCampaignData, facebook);
        self.setInstagramDetailView(template, selectedCampaignData, instagram);
        self.setGoogleDetailView(template, selectedCampaignData, google);

        $('section.campaignDetail').show();
    })
};

/**
 * Cleans the Detail View, removing the html of the different platforms
 *
 * @param facebook
 * @param instagram
 * @param google
 */
CampaignsView.prototype.cleanDetailView = function (facebook, instagram, google) {
    facebook.html("");
    facebook.closest("fieldset").hide();
    instagram.html("");
    instagram.closest("fieldset").hide();
    google.html("");
    google.closest("fieldset").hide();
};

/**
 * Sets the facebook platform view with the template and the data passed by parameter
 *
 * @param template
 * @param data
 * @param facebook
 */
CampaignsView.prototype.setFacebookDetailView = function (template, data, facebook) {
    const self = this;
    const facebookData = data.platforms.facebook;

    if (facebookData !== undefined) {
        self.setViewHtml(template, facebookData, facebook);
    }
};

/**
 * Sets the instagram platform view with the template and the data passed by parameter
 *
 * @param template
 * @param data
 * @param instagram
 */
CampaignsView.prototype.setInstagramDetailView = function (template, data, instagram) {
    const self = this;
    const instagramData = data.platforms.instagram;

    if (instagramData !== undefined) {
        self.setViewHtml(template, instagramData, instagram);
    }
};

/**
 * Sets the google platform view with the template and the data passed by parameter
 *
 * @param template
 * @param data
 * @param google
 */
CampaignsView.prototype.setGoogleDetailView = function (template, data, google) {
    const self = this;
    const googleData = data.platforms.google;

    if (googleData !== undefined) {
        self.setViewHtml(template, googleData, google);
    }
};

/**
 * Sets the html to the element with the template and the data passed by parameter
 *
 * @param template
 * @param data
 * @param element
 */
CampaignsView.prototype.setViewHtml = function (template, data, element) {
    const html = Mustache.to_html(template, data);
    element.html(html);
    element.closest("fieldset").show();
};

/**
 * Template to be used in the detail view
 *
 * @returns {string}
 */
CampaignsView.prototype.getDetailViewTemplate = function () {
    return "<div class='row'>\n" +
        "    <div class='column'>\n" +
        "        <fieldset>\n" +
        "            <legend class='subtitle'>General Information</legend> <span class='field'><span class='label'> Status:</span> {{status}}</span> <span\n" +
        "                class='field'><span class='label'> Total Budget:</span> {{total_budget}}</span> <span class='field'><span class='label'>\n" +
        "                Remaining Budget:</span> {{remaining_budget}}</span> <span class='field'><span class='label'> Start Date:</span>\n" +
        "                {{start_date}}</span> <span class='field'><span class='label'> End Date:</span> {{end_date}}</span> </fieldset>\n" +
        "    </div>\n" +
        "    <div class='column'>\n" +
        "        <fieldset>\n" +
        "            <legend class='subtitle'>Target Audiance</legend> <span class='field'><span class='label'> Languages:</span> {{target_audiance.languages}}</span>\n" +
        "            <span class='field'><span class='label'> Genders:</span> {{target_audiance.genders}}</span> <span class='field'><span\n" +
        "                    class='label'> Age Range:</span> {{target_audiance.age_range}}</span> <span class='field'><span class='label'>\n" +
        "                Locations:</span> {{target_audiance.locations}}</span> <span class='field'><span class='label'> KeyWords:</span>\n" +
        "                {{target_audiance.KeyWords}}</span> <span class='field'><span class='label'> Interests:</span> {{target_audiance.interests}}</span>\n" +
        "            </fieldset>\n" +
        "    </div>\n" +
        "</div>\n" +
        "<div class='row'>\n" +
        "    <div class='column'>\n" +
        "        <fieldset>\n" +
        "            <legend class='subtitle'>Creatives</legend> {{#creatives.header}}<span class='field'><span class='label'> Header:</span> {{creatives.header}}</span>{{/creatives.header}}\n" +
        "            {{#creatives.header_1}}<span class='field'><span class='label'> Header 1:</span> {{creatives.header_1}}</span>{{/creatives.header_1}}\n" +
        "            {{#creatives.header_2}}<span class='field'><span class='label'> Header 2:</span> {{creatives.header_2}}</span>{{/creatives.header_2}}\n" +
        "            <span class='field'><span class='label'> Description:</span> {{creatives.description}}</span> <span class='field'><span\n" +
        "                    class='label'> Url:</span> <a href='{{creatives.url}}'>{{creatives.url}}</a></span> {{#creatives.image}}<img\n" +
        "                class='campaignImage' src='http://localhost:8080/api/campaigns/images?name={{creatives.image}}'>{{/creatives.image}}\n" +
        "            </fieldset>\n" +
        "    </div>\n" +
        "    <div class='column'>\n" +
        "        <fieldset>\n" +
        "            <legend class='subtitle'>Insights</legend> <span class='field'><span class='label'> Impressions:</span> {{insights.impressions}}</span>\n" +
        "            <span class='field'><span class='label'> Clicks:</span> {{insights.clicks}}</span> <span class='field'><span\n" +
        "                    class='label'> Website Visits:</span> {{insights.website_visits}}</span> <span class='field'><span class='label'>\n" +
        "                Score:</span> {{insights.score}}</span> <span class='field'><span class='label'> Cost Per Click:</span>\n" +
        "                {{insights.cost_per_click}}</span> <span class='field'><span class='label'> Click Through Rate:</span> {{insights.click_through_rate}}</span>\n" +
        "            <span class='field'><span class='label'> Advanced KPI 1:</span> {{insights.advanced_kpi_1}}</span> <span class='field'><span\n" +
        "                    class='label'> Advanced KPI 2:</span> {{insights.advanced_kpi_2}}</span> </fieldset>\n" +
        "    </div>\n" +
        "</div>\n" +
        "</fieldset>";
};