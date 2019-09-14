package af.dfi.core.kafka.connect;

import af.dfi.lang.aspect.Loggable;
import af.dfi.lang.util.Utility;
import org.sourcelab.kafka.connect.apiclient.Configuration;
import org.sourcelab.kafka.connect.apiclient.KafkaConnectClient;
import org.sourcelab.kafka.connect.apiclient.request.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * API Client for interacting with the Kafka-Connect Rest Endpoint.
 * Official Rest Endpoint documentation can be found here:
 *   https://docs.confluent.io/current/connect/references/restapi.html
 */

@Service
public class KafkaConnectIntegrationService {

    @Value("${kafka.connect.host}")
    private String kafkaConnectHost;
    private Utility utility = new Utility();

    /*
     * Create a new configuration object.
     *
     * This configuration also allows you to define some optional details on your connection,
     * such as using an outbound proxy (authenticated or not), SSL client settings, etc..
     */
    @Loggable
    public Configuration configuration(){
        final Configuration config = new Configuration(kafkaConnectHost);
        return config;
    }

    /*
     * Create an instance of KafkaConnectClient, passing your configuration.
     */
    @Loggable
    public KafkaConnectClient client()
    {
        final KafkaConnectClient client = new KafkaConnectClient(configuration());
        return client;
    }


    /*
     * Making requests by calling the public methods available on ApiClient.
     *
     * For example, get a list of deployed connectors:
     */
    @Loggable
    @Retryable
    public Collection<String> getConnectors()
    {
        return client().getConnectors();
    }

    @Loggable
    @Retryable
    public Collection<String> getSourceConnectors()
    {
        Collection<String> sourceConnectors = new ArrayList();
        for (String con :  client().getConnectors()) {
            if(this.getConnector(con).getType().equalsIgnoreCase("source"))
            {
                sourceConnectors.add(con);
            }
        }
        return sourceConnectors;
    }


    @Loggable
    @Retryable
    public Collection<String> getSinkConnectors()
    {
        Collection<String> sinkConnectors = new ArrayList();
        for (String con : client().getConnectors()) {
            if(this.getConnector(con).getType().equalsIgnoreCase("sink"))
            {
                sinkConnectors.add(con);
            }
        }
        return sinkConnectors;
    }


    /*
     * Or to deploy a new connector:
     */
    @Loggable
    @Retryable
    public ConnectorDefinition deployConnector(String name, String configString)
    {
        try{
            // Map<String, String> config = jsonParserUtil.jsonToMap(jsonParserUtil.parse(configString));
            Map<String, String> config = utility.convertJsonStringToMap(configString);

            final ConnectorDefinition connectorDefition = client().addConnector(NewConnectorDefinition.newBuilder()
                    .withName(name)
                    .withConfig(config)
                    .build()
            );
            return connectorDefition;
        }catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Get information about the connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)
     * @param connectorName Name of connector.
     * @return Connector details.
     */
    @Loggable
    @Retryable
    public ConnectorDefinition getConnector(String connectorName) {
        return client().getConnector(connectorName);
    }

    /**
     * Get the configuration for the connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)-config
     * @param connectorName Name of connector.
     * @return Configuration for connector.
     */
    @Loggable
    @Retryable
    public Map<String, String> getConnectorConfig(final String connectorName) {
        return client().getConnectorConfig(connectorName);
    }

    /**
     * Get the status of specified connector by name.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)-config
     *
     * @param connectorName Name of connector.
     * @return Status details of the connector.
     */
    @Loggable
    @Retryable
    public ConnectorStatus getConnectorStatus(final String connectorName) {
        return client().getConnectorStatus(connectorName);
    }

    /**
     * Create a new connector, returning the current connector info if successful.
     * https://docs.confluent.io/current/connect/references/restapi.html#post--connectors
     *
     * @param connectorDefinition Defines the new connector to deploy
     * @return connector info.
     */
    @Loggable
    @Retryable
    public ConnectorDefinition addConnector(final NewConnectorDefinition connectorDefinition) {
        return client().addConnector(connectorDefinition);
    }

    /**
     * Update a connector's configuration.
     * https://docs.confluent.io/current/connect/references/restapi.html#put--connectors-(string-name)-config
     *
     * @param connectorName Name of connector to update.
     * @param config Configuration values to set.
     * @return ConnectorDefinition describing the connectors configuration.
     */
    @Loggable
    @Retryable
    public ConnectorDefinition updateConnectorConfig(final String connectorName, final Map<String, String> config) {
        return client().updateConnectorConfig(connectorName, config);
    }

    /**
     * Restart a connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#post--connectors-(string-name)-restart
     *
     * @param connectorName Name of connector to restart.
     * @return Boolean true if success.
     */
    @Loggable
    @Retryable
    // @Async
    public Boolean restartConnector(String connectorName) {
        return client().restartConnector(connectorName);
    }

    /**
     * Pause a connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#put--connectors-(string-name)-pause
     *
     * @param connectorName Name of connector to pause.
     * @return Boolean true if success.
     */
    @Loggable
    @Retryable
    public Boolean pauseConnector(final String connectorName) {
        return client().pauseConnector(connectorName);
    }

    /**
     * Resume a connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#put--connectors-(string-name)-resume
     *
     * @param connectorName Name of connector to resume.
     * @return Boolean true if success.
     */
    @Loggable
    @Retryable
    public Boolean resumeConnector(final String connectorName) {
        return client().resumeConnector(connectorName);
    }

    /**
     * Resume a connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#put--connectors-(string-name)-resume
     *
     * @param connectorName Name of connector to resume.
     * @return Boolean true if success.
     */
    @Loggable
    @Retryable
    public Boolean deleteConnector(final String connectorName) {
        return client().deleteConnector(connectorName);
    }

    /**
     * Get a list of tasks currently running for the connector.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)-tasks
     *
     * @param connectorName Name of connector to retrieve tasks for.
     * @return Collection of details about each task.
     */
    @Loggable
    @Retryable
    public Collection<Task> getConnectorTasks(final String connectorName) {
        return client().getConnectorTasks(connectorName);
    }

    /**
     * Get a task’s status.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connectors-(string-name)-tasks-(int-taskid)-status
     *
     * @param connectorName Name of connector to retrieve tasks for.
     * @param taskId Id of task to get status for.
     * @return Details about task.
     */
    @Loggable
    @Retryable
    public TaskStatus getConnectorTaskStatus(final String connectorName, final int taskId) {
        return client().getConnectorTaskStatus(connectorName, taskId);
    }


    /**
     * Restart an individual task.
     * https://docs.confluent.io/current/connect/references/restapi.html#post--connectors-(string-name)-tasks-(int-taskid)-restart
     *
     * @param connectorName Name of connector to restart tasks for.
     * @param taskId Id of task to restart
     * @return True if a success.
     */
    @Loggable
    @Retryable
    public Boolean restartConnectorTask(final String connectorName, final int taskId) {
        return client().restartConnectorTask(connectorName, taskId);
    }

    /**
     * Return a list of connector plugins installed in the Kafka Connect cluster.
     * https://docs.confluent.io/current/connect/references/restapi.html#get--connector-plugins-
     *
     * @return Collection of available connector plugins.
     */
    @Loggable
    @Retryable
    public Collection<ConnectorPlugin> getConnectorPlugins() {
        return client().getConnectorPlugins();
    }

    /**
     * Validate the provided configuration values against the configuration definition. This API performs per config
     * validation, returns suggested values and error messages during validation.
     * https://docs.confluent.io/current/connect/references/restapi.html#put--connector-plugins-(string-name)-config-validate
     *
     * @param configDefinition Defines the configuration to validate.
     * @return Results of the validation.
     */
    @Loggable
    @Retryable
    public ConnectorPluginConfigValidationResults validateConnectorPluginConfig(final ConnectorPluginConfigDefinition configDefinition) {
        return client().validateConnectorPluginConfig(configDefinition);
    }

}