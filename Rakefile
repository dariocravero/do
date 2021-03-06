require 'pathname'
require 'rubygems'
require 'rake'

ROOT     = Pathname(__FILE__).dirname.expand_path
JRUBY    = RUBY_PLATFORM =~ /java/
IRONRUBY = defined?(RUBY_ENGINE) && RUBY_ENGINE == 'ironruby'
WINDOWS  = Gem.win_platform? || (JRUBY && ENV_JAVA['os.name'] =~ /windows/i)
SUDO     = WINDOWS ? '' : ('sudo' unless ENV['SUDOLESS'])

# RCov is run by default, except on the JRuby and IronRuby platforms, or if NO_RCOV env is true
RUN_RCOV = JRUBY || IRONRUBY ? false : (ENV.has_key?('NO_RCOV') ? ENV['NO_RCOV'] != 'true' : true)

jruby_projects = %w[do_jdbc do_derby do_h2 do_hsqldb]
projects = %w[data_objects]
projects += %w[do_mysql do_postgres do_sqlite3 do_sqlserver do_oracle]
projects += jruby_projects if JRUBY

def rake(cmd)
  ruby "-S rake #{cmd}", :verbose => false
end

desc 'Release all do gems'
task :release do
  (jruby_projects + projects).uniq.each do |dir|
    Dir.chdir(dir){ rake "release_all" }
  end
end

tasks = {
  :install   => 'Install the do gems',
  :build_all => 'Package the do gems',
  :clean     => 'clean temporary files',
  :clobber   => 'clobber temporary files',
}

task :default => [:spec]

desc 'Run all the specs for the subprojects'
task :spec do

  commands = [
    'mysql -u root -e "create database do_test;"',
    'psql  -c "create database do_test;" -U postgres',
  ]

  commands.each do |command|
    `#{command}`
  end

  spec_projects = %w[do_mysql do_postgres do_sqlite3]
  if JRUBY
    spec_projects += %w[do_derby do_h2 do_hsqldb]
    Dir.chdir("do_jdbc") { rake :compile }
  end

  spec_projects.each do |gem_name|
    Dir.chdir(gem_name) { rake :spec }
  end

end

tasks.each do |name, description|
  desc description
  task name do
    (jruby_projects + projects).each do |gem_name|
      Dir.chdir(gem_name){ rake name }
    end
  end
end
