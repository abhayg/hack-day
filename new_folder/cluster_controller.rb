require 'java'
Dir[File.join(PADRINO_ROOT, "lib/jars/*.jar")].each { |jar| require jar }

Nss.controllers :cluster do
  get '/get_html' do
    render 'cluster/index'
  end

  get '/get_clusters', :provides => [:json] do
    data_points = params["latlng_points"].values.map {|item| item.map {|item2| item2.to_f}}
    response = Java::ComFlipkartClusters::Clusters.new.getClusters(data_points, params[:num_clusters].to_i)
    p response.to_a.map {|item| item.to_a.map {|item2| item2.to_a}}

    response.to_a.map {|item| item.to_a.map {|item2| item2.to_a}}.to_json
  end
end
