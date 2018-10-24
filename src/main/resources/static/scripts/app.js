/**
 * Calls the Campaigns API endpoint to get the data from the database
 * Sets the Data and View and initialise the events on the view
 */
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/api/campaigns"
    }).then(function(data) {
        const campaignsData = new CampaignsData(data);
        const campaignsView = new CampaignsView(campaignsData);

        campaignsView.initEvents();
    });
});
